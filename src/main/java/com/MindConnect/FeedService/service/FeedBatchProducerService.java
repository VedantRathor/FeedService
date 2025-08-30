package com.MindConnect.FeedService.service;

import com.MindConnect.FeedService.common.FeedBatchSentEvent;
import com.MindConnect.FeedService.config.KafkaProducerConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBatchProducerService {
    private final KafkaTemplate<String, FeedBatchSentEvent> kafkaTemplate;

    public FeedBatchProducerService(KafkaTemplate<String, FeedBatchSentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFeedBatchEvent(FeedBatchSentEvent batch) {
        this.kafkaTemplate.send(KafkaProducerConfig.FEED_BATCH_TOPIC, batch);
    }
}
