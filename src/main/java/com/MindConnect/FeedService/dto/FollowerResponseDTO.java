package com.MindConnect.FeedService.dto;

public class FollowerResponseDTO {
    private boolean success;
    private FollowDTO data;
    private String message;

    public FollowerResponseDTO() {}

    public FollowerResponseDTO(boolean success, FollowDTO data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public FollowDTO getData() {
        return data;
    }

    public void setData(FollowDTO data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
