package com.MindConnect.FeedService.common;

import com.MindConnect.FeedService.entity.TimelineEntity;
import java.util.List;

public class FeedBatchSentEvent {
    private List<TimelineEntity> timelineEntities;

    public FeedBatchSentEvent() {}

    public FeedBatchSentEvent(List<TimelineEntity> timelineEntities) {
        this.timelineEntities = timelineEntities;
    }

    public List<TimelineEntity> getTimelineEntities() {
        return timelineEntities;
    }

    public void setTimelineEntities(List<TimelineEntity> timelineEntities) {
        this.timelineEntities = timelineEntities;
    }
}
