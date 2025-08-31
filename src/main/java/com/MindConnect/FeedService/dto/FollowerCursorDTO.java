package com.MindConnect.FeedService.dto;

public class FollowerCursorDTO {
    private String followerId;
    private String cursor;

    public FollowerCursorDTO() {}

    public FollowerCursorDTO(String followerId, String cursor) {
        this.followerId = followerId;
        this.cursor = cursor;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }
}
