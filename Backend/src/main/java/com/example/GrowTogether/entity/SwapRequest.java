package com.example.GrowTogether.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "swap_request")
public class SwapRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;
    private Long receiverId;
    private String skillOffered;
    private String skillRequested;
    private String status;

    public SwapRequest() {
        this.status = "Pending";
    }

    public SwapRequest(Long senderId, Long receiverId, String skillOffered, String skillRequested, String status) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.skillOffered = skillOffered;
        this.skillRequested = skillRequested;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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