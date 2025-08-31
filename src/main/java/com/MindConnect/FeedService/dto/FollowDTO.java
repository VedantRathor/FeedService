package com.MindConnect.FeedService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FollowDTO {
    private String userId;
    @JsonProperty("followers")
    private List<FollowerCursorDTO> followers;
    private String lastCursor;
    private Long batchSize;
    private String nextCursor;

    public FollowDTO() {}

    public FollowDTO(String userId, Long batchSize, String lastCursor, List<FollowerCursorDTO> followers, String nextCursor) {
        this.userId = userId;
        this.batchSize = batchSize;
        this.lastCursor = lastCursor;
        this.followers = followers;
        this.nextCursor = nextCursor;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(String nextCursor) {
        this.nextCursor = nextCursor;
    }

    public Long getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Long batchSize) {
        this.batchSize = batchSize;
    }

    public String getLastCursor() {
        return lastCursor;
    }

    public void setLastCursor(String lastCursor) {
        this.lastCursor = lastCursor;
    }

    public List<FollowerCursorDTO> getFollowersList() {
        return followers;
    }

    public void setFollowersList(List<FollowerCursorDTO> followers) {
        this.followers = followers;
    }
}
