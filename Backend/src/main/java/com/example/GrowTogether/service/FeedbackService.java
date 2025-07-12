package com.example.GrowTogether.service;

import com.example.GrowTogether.dto.FeedbackDTO;
import java.util.List;

public interface FeedbackService {
    FeedbackDTO submitFeedback(FeedbackDTO feedbackDTO);
    List<FeedbackDTO> getAllFeedbacks();
}