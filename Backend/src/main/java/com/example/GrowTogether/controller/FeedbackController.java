package com.example.GrowTogether.controller;

import com.example.GrowTogether.dto.FeedbackDTO;
import com.example.GrowTogether.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackDTO> submitFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO saved = feedbackService.submitFeedback(feedbackDTO);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }
}
