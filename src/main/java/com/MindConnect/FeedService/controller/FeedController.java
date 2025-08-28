package com.MindConnect.FeedService.controller;

import com.MindConnect.FeedService.service.FeedService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedController {
    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }
}
