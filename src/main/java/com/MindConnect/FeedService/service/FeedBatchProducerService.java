package com.MindConnect.FeedService.service;

import com.MindConnect.FeedService.config.KafkaProducerConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FeedBatchProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public FeedBatchProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFeedBatchEvent(String message) {
        this.kafkaTemplate.send(KafkaProducerConfig.FEED_BATCH_TOPIC, message);
    }
}
