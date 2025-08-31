package com.MindConnect.FeedService.consumer;

import com.MindConnect.FeedService.Limiter.MongoWriteRateLimiter;
import com.MindConnect.FeedService.common.FeedBatchSentEvent;
import com.MindConnect.FeedService.repository.TimelineRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

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
    public void consume(FeedBatchSentEvent event, Acknowledgment acknowledgment)  {
        try {
            // Use Guava RateLimiter
            System.out.println("[WAITING] for tokens...");
            MongoWriteRateLimiter.acquire(event.getTimelineEntities().size());

            timelineRepository.saveAll(event.getTimelineEntities());
            System.out.println("[SUCCESS] Inserted " + event.getTimelineEntities().size() + " timelines into MongoDB.");
            acknowledgment.acknowledge();
        } catch (Exception e) {
            // log error, Spring Kafka will handle retry + DLQ
            throw e;
        }
    }
}
