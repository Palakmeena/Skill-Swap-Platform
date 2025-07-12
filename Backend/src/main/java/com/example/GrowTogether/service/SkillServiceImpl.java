package com.example.GrowTogether.service;


import com.example.GrowTogether.dto.SkillDTO;
import com.example.GrowTogether.entity.Skill;
import com.example.GrowTogether.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public SkillDTO createSkill(SkillDTO skillDTO) {
        Skill skill = new Skill();
        skill.setName(skillDTO.getName());

        Skill saved = skillRepository.save(skill);
        return new SkillDTO(saved.getId(), saved.getName());
    }

    @Override
    public List<SkillDTO> getAllSkills() {
        List<Skill> skills = skillRepository.findAll();
        return skills.stream()
                .map(s -> new SkillDTO(s.getId(), s.getName()))
                .collect(Collectors.toList());
    }
}
