package com.MindConnect.FeedService.consumer;

import com.MindConnect.FeedService.common.PostEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class PostEventConsumer {

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

//            ExecutorService executor = Executors.newFixedThreadPool(3);
//
//            for (int thread = 1; thread <= 3; thread ++) {
//                executor.submit(()-> {
//                    try {
//                        System.out.println(1);
//                        myHeavyFunc();
//                        System.out.println(2);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//            }
//            System.out.println(3);
//            executor.shutdown();
//            System.out.println(4);
//            executor.awaitTermination(100, TimeUnit.SECONDS);
//            System.out.println(5);

            acknowledgment.acknowledge(); // commit offset manually after successful processing
        } catch (Exception e) {
            // log error, Spring Kafka will handle retry + DLQ
            throw e;
        }
    }

    private static void myHeavyFunc() throws InterruptedException {
        Thread.sleep(2000);
    }
}
