package com.example.GrowTogether.dto;



public class SwapRequestDTO {
    private Long senderId;
    private Long receiverId;
    private String skillOffered;
    private String skillRequested;
    private String status;

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getSkillOffered() {
        return skillOffered;
    }

    public void setSkillOffered(String skillOffered) {
        this.skillOffered = skillOffered;
    }

    public String getSkillRequested() {
        return skillRequested;
    }

    public void setSkillRequested(String skillRequested) {
        this.skillRequested = skillRequested;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}