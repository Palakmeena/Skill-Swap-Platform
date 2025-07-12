package com.example.GrowTogether.repository;

import com.example.GrowTogether.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}