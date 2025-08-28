package com.MindConnect.FeedService.service;

import com.MindConnect.FeedService.repository.TimelineRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedService {
    private final TimelineRepository timelineRepository;

    public FeedService(TimelineRepository timelineRepository) {
        this.timelineRepository = timelineRepository;
    }
}
