package com.MindConnect.FeedService.repository;

import com.MindConnect.FeedService.entity.TimelineEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimelineRepository extends MongoRepository<TimelineEntity, String> {

}
