package com.example.GrowTogether.service;

import com.example.GrowTogether.dto.SkillDTO;
import java.util.List;

public interface SkillService {
    SkillDTO createSkill(SkillDTO skillDTO);
    List<SkillDTO> getAllSkills();
}