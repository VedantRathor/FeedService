package com.MindConnect.FeedService.common;

import org.springframework.stereotype.Component;

import java.time.Instant;

public class PostEvent {
    private String postId;
    private String creatorId;
    private Instant postCreatedAt;

    public PostEvent() {}

    public PostEvent(String postId, String creatorId, Instant postCreatedAt) {
        this.postId = postId;
        this.creatorId = creatorId;
        this.postCreatedAt = postCreatedAt;
    }

    public Instant getPostCreatedAt() {
        return postCreatedAt;
    }

    public void setPostCreatedAt(Instant postCreatedAt) {
        this.postCreatedAt = postCreatedAt;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

}
