package com.MindConnect.FeedService.consumer;

import com.MindConnect.FeedService.Limiter.MongoWriteRateLimiter;
import com.MindConnect.FeedService.common.FeedBatchSentEvent;
import com.MindConnect.FeedService.entity.TimelineEntity;
import com.MindConnect.FeedService.repository.TimelineRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedBatchEventConsumer {
    private final TimelineRepository timelineRepository;

    public FeedBatchEventConsumer(TimelineRepository timelineRepository) {
        this.timelineRepository = timelineRepository;
    }

    @KafkaListener(
            topics = "feed-batch-topic",
            groupId = "feed-batch-group-1",
            containerFactory = "feedBatchTopicConsumerFactoryKafkaListenerContainerFactory"
    )
    public void consume(FeedBatchSentEvent event, Acknowledgment acknowledgment) throws InterruptedException {
        try {
//            System.out.println("Consumed Event................");
//            System.out.println("Post Id: " + event.getPostId());
//            for (String follower: event.getFollowerList()) {
//                System.out.println("Follower Id: " + follower);
//            }

            List<TimelineEntity> entities = event.getFollowerList().stream()
                    .map(followerId -> {
                            TimelineEntity entity = new TimelineEntity();
                            entity.setAuthorId(event.getCreatorId());
                            entity.setPostId(event.getPostId());
                            entity.setPostCreatedAt(event.getPostCreatedAt());
                            entity.setUserId(followerId);
                            return entity;
                    }).toList();

            // Use Guava RateLimiter
            System.out.println("[PICKED WAIT] waiting for permits");
            MongoWriteRateLimiter.acquire(entities.size());

            timelineRepository.saveAll(entities);
            System.out.println("[SUCCESS] Inserted " + entities.size() + " timelines into MongoDB.");
            acknowledgment.acknowledge();
        } catch (Exception e) {
            // log error, Spring Kafka will handle retry + DLQ
            throw e;
        }
    }
}
