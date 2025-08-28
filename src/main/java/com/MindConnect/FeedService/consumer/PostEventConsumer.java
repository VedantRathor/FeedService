package com.MindConnect.FeedService.consumer;

import com.MindConnect.FeedService.common.PostEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class PostEventConsumer {

    @KafkaListener(
            topics = "post-events",
            groupId = "feed-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(PostEvent event, Acknowledgment acknowledgment) {
        try {
            System.out.println(event.getPostId());
            System.out.println(event.getCreatorId());
            System.out.println(event.getPostCreatedAt());
            acknowledgment.acknowledge(); // commit offset manually after successful processing
        } catch (Exception e) {
            // log error, Spring Kafka will handle retry + DLQ
            throw e;
        }
    }
}
