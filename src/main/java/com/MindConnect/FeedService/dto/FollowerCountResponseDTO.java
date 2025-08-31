package com.MindConnect.FeedService.dto;

public class FollowerCountResponseDTO {
    private boolean success;
    private Long data;
    private String message;

    public FollowerCountResponseDTO() { }

    public FollowerCountResponseDTO(Long data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
