package com.MindConnect.FeedService.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "timelines")
@CompoundIndexes({
        // Ensure uniqueness of userId + postId (no duplicate post in a user's timeline)
        @CompoundIndex(name = "unique_user_post", def = "{'userId': 1, 'postId': 1}", unique = true),
        // Optimize queries by userId + postCreatedAt (used in timeline fetch with pagination)
        @CompoundIndex(name = "user_postCreatedAt_idx", def = "{'userId': 1, 'postCreatedAt': -1}")
})
public class TimelineEntity {
    @Id
    private String id; // MongoDB document-id

    @NotBlank(message = "UserId must not be empty")
    private String userId; //  The feed belongs to which user

    @NotBlank(message = "PostId must not be empty")
    private String postId; // The post to be shown

    @NotBlank(message = "AuthorId must not be empty")
    private String authorId; // The creator of the post

    @NotNull(message = "PostCreationTime must not be null or empty")
    private Instant postCreatedAt; // CreatedAt for the post (used for time-based cursor pagination)

    @NotNull(message = "CreatedAt must not be null or empty")
    @CreatedDate
    private Instant createdAt;

    public TimelineEntity() {}

    public TimelineEntity(String userId, String postId, String authorId, Instant postCreatedAt) {
        this.userId = userId;
        this.postId = postId;
        this.authorId = authorId;
        this.postCreatedAt = postCreatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Instant getPostCreatedAt() {
        return postCreatedAt;
    }

    public void setPostCreatedAt(Instant postCreatedAt) {
        this.postCreatedAt = postCreatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void  setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
