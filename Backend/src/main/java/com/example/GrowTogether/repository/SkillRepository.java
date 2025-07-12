package com.example.GrowTogether.repository;

import com.example.GrowTogether.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}