package com.MindConnect.FeedService.config;

import com.MindConnect.FeedService.common.FeedBatchSentEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaFeedBatchTopicConsumerConfig {

    @Bean
    public ConsumerFactory<String, FeedBatchSentEvent> feedBatchTopicConsumerFactory() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        JsonDeserializer<FeedBatchSentEvent> valueDeserializer = new JsonDeserializer<>(FeedBatchSentEvent.class);
        valueDeserializer.addTrustedPackages("*"); // allow deserialization of our package

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), valueDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FeedBatchSentEvent> feedBatchTopicConsumerFactoryKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FeedBatchSentEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(feedBatchTopicConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        // Manual ack mode → consumer can commit offsets after processing, avoids losing messages on failure

        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000); // poll interval
        return factory;
    }
}
