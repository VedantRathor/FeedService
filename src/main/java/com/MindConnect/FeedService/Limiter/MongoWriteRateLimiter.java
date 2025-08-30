package com.MindConnect.FeedService.Limiter;

import com.google.common.util.concurrent.RateLimiter;

public class MongoWriteRateLimiter {
    private static final RateLimiter rateLimiter = RateLimiter.create(6000.0);
    private MongoWriteRateLimiter() {}

    public static void acquire(int permits) {
        rateLimiter.acquire(permits); // blocks until permits are available
    }
}
