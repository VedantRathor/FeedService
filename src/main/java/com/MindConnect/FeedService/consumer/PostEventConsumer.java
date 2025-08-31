package com.MindConnect.FeedService.consumer;

import com.MindConnect.FeedService.client.FollowServiceClient;
import com.MindConnect.FeedService.common.FeedBatchSentEvent;
import com.MindConnect.FeedService.common.PostEvent;
import com.MindConnect.FeedService.dto.FollowerResponseDTO;
import com.MindConnect.FeedService.dto.FollowerCursorDTO;
import com.MindConnect.FeedService.entity.TimelineEntity;
import com.MindConnect.FeedService.repository.TimelineRepository;
import com.MindConnect.FeedService.service.FeedBatchProducerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import java.util.List;



@Component
public class PostEventConsumer {
    private final FeedBatchProducerService feedBatchProducerService;
    private final TimelineRepository timelineRepository;
    private final FollowServiceClient followServiceClient;

    public PostEventConsumer(FeedBatchProducerService feedBatchProducerService, TimelineRepository timelineRepository, FollowServiceClient followServiceClient) {
        this.timelineRepository = timelineRepository;
        this.feedBatchProducerService = feedBatchProducerService;
        this.followServiceClient = followServiceClient;
    }

    @KafkaListener(
            topics = "post-events",
            groupId = "feed-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(PostEvent event, Acknowledgment acknowledgment) throws InterruptedException {
        try {
            System.out.println(event.getPostId());
            System.out.println(event.getCreatorId());
            System.out.println(event.getPostCreatedAt());

            int threshold = 10000;
            Long batchSize = 2000L;
            String userId = event.getCreatorId();

            // Get the followerCount
            Long followerCount = followServiceClient.getFollowerCount(userId).getData();
            System.out.println("followerCount: " + followerCount);

            if (followerCount <= threshold) {
                // Fetch all followers in one go
                FollowerResponseDTO response = followServiceClient.getFollowers(userId, null, followerCount);
                List<TimelineEntity> entities = response.getData().getFollowersList().stream()
                        .map(follower -> {
                            TimelineEntity entity = new TimelineEntity();
                            entity.setUserId(follower.getFollowerId());
                            entity.setPostCreatedAt(event.getPostCreatedAt());
                            entity.setPostId(event.getPostId());
                            entity.setAuthorId(event.getCreatorId());
                            return entity;
                        }).toList();

                if (entities != null && !entities.isEmpty()) {
                    this.timelineRepository.saveAll(entities);
                }
            } else {
                // Cursor pagination
                String cursor = null;
                boolean hasNext = true;

                while (hasNext) {
                    FollowerResponseDTO response = followServiceClient.getFollowers(userId, cursor, batchSize);
                    List<FollowerCursorDTO> followers = response.getData().getFollowersList();

                    if (followers != null && !followers.isEmpty()) {
                        List<TimelineEntity> batch = followers.stream()
                                .map(follower -> {
                                    TimelineEntity entity = new TimelineEntity();
                                    entity.setUserId(follower.getFollowerId());
                                    entity.setPostCreatedAt(event.getPostCreatedAt());
                                    entity.setPostId(event.getPostId());
                                    entity.setAuthorId(event.getCreatorId());
                                    return entity;
                                }).toList();

                        this.feedBatchProducerService.sendFeedBatchEvent(new FeedBatchSentEvent(batch));
                        System.out.println("[INSERTED] into partition..." );
                    }

                    cursor = response.getData().getNextCursor();
                    hasNext = cursor != null;
                }
            }

            acknowledgment.acknowledge();
        } catch (Exception e) {
            // log error, Spring Kafka will handle retry + DLQ
            throw e;
        }
    }
}
