package com.MindConnect.FeedService.consumer;

import com.MindConnect.FeedService.common.PostEvent;
import com.MindConnect.FeedService.service.FeedBatchProducerService;
import org.springframework.data.repository.query.ExtensionAwareQueryMethodEvaluationContextProvider;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

//            List<Integer> followerList = new ArrayList<>();
//            for (int i = 0; i < 5000000; i++) {
//                followerList.add(i);
//            }
//            int totalFollowers = followerList.size();
//            int threshold = 2000;
//            int batchSize = 2000;
//
//            if (totalFollowers > threshold) {
//                int numberOfThreads = 10;
//                int loadPerThread = totalFollowers / 10;
//                ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
//                List<Integer> followersListIDs = new ArrayList<>();
//                for (int i = 0; i < totalFollowers; i++) {
//                    followersListIDs.add(i+1);
//                }
//
//                long startTime = System.currentTimeMillis();
//
//                for (int thread = 0; thread < 10; thread++) {
//                    int start = thread * loadPerThread;
//                    int end = Math.min(start+loadPerThread, followerList.size());
////                    System.out.println("start: " + start);
////                    System.out.println("end: " + end);
//                    executorService.submit(()-> {
//                        int fromStart = start;
////                        System.out.println("fromStart: " + fromStart);
////                        System.out.println("toEND: " + fromStart + batchSize);
//                        int batchsize = 2000;
//                        while (true) {
//                            if (fromStart + batchsize > end) {
//                                int skip = fromStart;
//                                int limit = end - fromStart;
//                                List<Integer> filtered = followersListIDs.subList(skip, skip+limit);
//                                for (Integer id: filtered) {  }
//
//                                try {
//                                    Thread.sleep(70);
//                                } catch (InterruptedException e) {
//                                    throw new RuntimeException(e);
//                                }
//                                break;
//                            } else {
//                                int skip = fromStart;
//                                int limit = batchsize;
//                                // do db stuff
//                                List<Integer> filtered = followersListIDs.subList(skip, skip+limit);
//                                for (Integer id: filtered) {  }
//
//                                fromStart = fromStart + batchsize;
//                                try {
//                                    Thread.sleep(70);
//                                } catch (InterruptedException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            }
//                        }
//                    });
//                }
//                executorService.shutdown();
//                executorService.awaitTermination(10, TimeUnit.MINUTES);
//                long endTime = System.currentTimeMillis();    // end timer
//                System.out.println("Total time taken: " + (endTime - startTime) + " ms");
//                System.out.println("EXITEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
//            }

        } catch (Exception e) {
            // log error, Spring Kafka will handle retry + DLQ
            throw e;
        }
    }

    private static void myHeavyFunc() throws InterruptedException {
        Thread.sleep(2000);
    }
}
