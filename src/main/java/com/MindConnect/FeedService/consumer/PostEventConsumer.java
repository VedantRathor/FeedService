package com.MindConnect.FeedService.consumer;

import com.MindConnect.FeedService.common.FeedBatchSentEvent;
import com.MindConnect.FeedService.common.PostEvent;
import com.MindConnect.FeedService.service.FeedBatchProducerService;
import org.springframework.data.repository.query.ExtensionAwareQueryMethodEvaluationContextProvider;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PostEventConsumer {
    private final FeedBatchProducerService feedBatchProducerService;

    public PostEventConsumer(FeedBatchProducerService feedBatchProducerService) {
        this.feedBatchProducerService = feedBatchProducerService;
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


            int times = 1000000 / 4000;
            while (times > 0) {
                List<String> followers = new ArrayList<>();
                times --;
                for (Integer user = 1; user <= 4000; user++) {
                    String userId = user.toString() + "-uuid" + "-unique" + "-" + System.nanoTime() + "-" + UUID.randomUUID();
                    followers.add(userId);
                }

                FeedBatchSentEvent batch = new FeedBatchSentEvent(
                        event.getPostId(),
                        event.getCreatorId(),
                        event.getPostCreatedAt(),
                        followers
                );

                feedBatchProducerService.sendFeedBatchEvent(batch);
                System.out.println("[INFO] inserted into topic.......");
                Thread.sleep(50);
            }

            acknowledgment.acknowledge();

        } catch (Exception e) {
            // log error, Spring Kafka will handle retry + DLQ
            throw e;
        }
    }

    private static void myHeavyFunc() throws InterruptedException {
        Thread.sleep(2000);
    }
}
