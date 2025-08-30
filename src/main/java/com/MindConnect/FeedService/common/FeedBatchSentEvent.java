package com.MindConnect.FeedService.common;

import java.time.Instant;
import java.util.List;

public class FeedBatchSentEvent {
    private String postId;
    private String creatorId;
    private Instant postCreatedAt;
    private List<String> followerList;

    public FeedBatchSentEvent() {}

    public FeedBatchSentEvent(String postId, String creatorId, Instant postCreatedAt, List<String> followerList) {
        this.postId = postId;
        this.creatorId = creatorId;
        this.postCreatedAt = postCreatedAt;
        this.followerList = followerList;
    }

    public String getPostId() {
        return postId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public Instant getPostCreatedAt() {
        return postCreatedAt;
    }

    public List<String> getFollowerList() {
        return followerList;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setPostCreatedAt(Instant postCreatedAt) {
        this.postCreatedAt = postCreatedAt;
    }

    public void setFollowerList(List<String> followerList) {
        this.followerList = followerList;
    }
}
