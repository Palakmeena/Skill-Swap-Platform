package com.example.GrowTogether.controller;

import com.example.GrowTogether.dto.SwapRequestDTO;
import com.example.GrowTogether.entity.SwapRequest;
import com.example.GrowTogether.service.SwapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/swap")
@CrossOrigin
public class SwapController {

    @Autowired
    private SwapService swapService;

    @PostMapping
    public SwapRequest createSwap(@RequestBody SwapRequestDTO dto) {
        return swapService.createSwapRequest(dto);
    }

    @GetMapping("/received/{userId}")
    public List<SwapRequest> getReceivedRequests(@PathVariable Long userId) {
        return swapService.getReceivedRequests(userId);
    }

    @GetMapping("/sent/{userId}")
    public List<SwapRequest> getSentRequests(@PathVariable Long userId) {
        return swapService.getSentRequests(userId);
    }

    @PutMapping("/{id}/status")
    public SwapRequest updateRequestStatus(@PathVariable Long id, @RequestParam String status) {
        return swapService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String deleteSwapRequest(@PathVariable Long id) {
        swapService.deleteSwapRequest(id);
        return "Swap request deleted successfully.";
    }
}