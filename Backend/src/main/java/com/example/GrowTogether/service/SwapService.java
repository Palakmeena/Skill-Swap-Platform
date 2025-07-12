package com.example.GrowTogether.service;

import com.example.GrowTogether.dto.SwapRequestDTO;
import com.example.GrowTogether.entity.SwapRequest;
import com.example.GrowTogether.exception.ResourceNotFoundException;
import com.example.GrowTogether.repository.SwapRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SwapService {

    @Autowired
    private SwapRequestRepository repository;@Autowired
    private SwapRequestRepository swapRequestRepository;


    public SwapRequest createSwapRequest(SwapRequestDTO dto) {
        SwapRequest request = new SwapRequest();
        request.setSenderId(dto.getSenderId());
        request.setReceiverId(dto.getReceiverId());
        request.setSkillOffered(dto.getSkillOffered());
        request.setSkillRequested(dto.getSkillRequested());
        request.setStatus("Pending");

        return repository.save(request);
    }

    public List<SwapRequest> getReceivedRequests(Long userId) {
        return repository.findByReceiverId(userId);
    }

    public List<SwapRequest> getSentRequests(Long userId) {
        return repository.findBySenderId(userId);
    }

    public SwapRequest updateStatus(Long id, String status) {
        Optional<SwapRequest> optionalRequest = repository.findById(id);
        if (optionalRequest.isPresent()) {
            SwapRequest request = optionalRequest.get();
            request.setStatus(status);
            return repository.save(request);
        } else {
            throw new RuntimeException("Swap request not found");
        }
    }


    public void deleteSwapRequest(Long id) {
        SwapRequest swap = swapRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SwapRequest not found with id " + id));
        swapRequestRepository.delete(swap);
    }
}