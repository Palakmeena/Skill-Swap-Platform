package com.example.GrowTogether.service;

import com.example.GrowTogether.dto.FeedbackDTO;
import com.example.GrowTogether.entity.Feedback;
import com.example.GrowTogether.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public FeedbackDTO submitFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setName(feedbackDTO.getName());
        feedback.setEmail(feedbackDTO.getEmail());
        feedback.setMessage(feedbackDTO.getMessage());

        Feedback saved = feedbackRepository.save(feedback);
        return new FeedbackDTO(saved.getId(), saved.getName(), saved.getEmail(), saved.getMessage());
    }

    @Override
    public List<FeedbackDTO> getAllFeedbacks() {
        return feedbackRepository.findAll().stream()
                .map(f -> new FeedbackDTO(f.getId(), f.getName(), f.getEmail(), f.getMessage()))
                .collect(Collectors.toList());
    }
}