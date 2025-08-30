package com.MindConnect.FeedService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaProducerConfig {
    public static final String FEED_BATCH_TOPIC = "feed-batch-topic"; // topic name

    @Bean
    public NewTopic feedBatchTopic() {
         return TopicBuilder.name(KafkaProducerConfig.FEED_BATCH_TOPIC)
                .partitions(3)
                .replicas(2)
                .build();
    }
}
