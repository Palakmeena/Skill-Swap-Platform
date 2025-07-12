package com.example.GrowTogether.repository;



import com.example.GrowTogether.entity.SwapRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SwapRequestRepository extends JpaRepository<SwapRequest, Long> {
    List<SwapRequest> findByReceiverId(Long receiverId);
    List<SwapRequest> findBySenderId(Long senderId);
}