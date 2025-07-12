package com.example.GrowTogether.controller;

import com.example.GrowTogether.dto.ProfileDTO;
import com.example.GrowTogether.entity.User;
import com.example.GrowTogether.service.UserService;
import com.example.GrowTogether.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityUtil securityUtil;

    // Get current user's profile
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile() {
        try {
            User currentUser = securityUtil.getCurrentUser();
            if (currentUser == null) {
                return ResponseEntity.status(401).body("User not authenticated");
            }

            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setName(currentUser.getName());
            profileDTO.setLocation(currentUser.getLocation());
            profileDTO.setProfilePhotoUrl(currentUser.getProfilePhotoUrl());
            profileDTO.setSkillsOffered(currentUser.getSkillsOffered());
            profileDTO.setSkillsWanted(currentUser.getSkillsWanted());
            profileDTO.setAvailability(currentUser.getAvailability());
            profileDTO.setPublic(currentUser.isPublic());

            return ResponseEntity.ok(profileDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching profile: " + e.getMessage());
        }
    }

    // Update current user's profile
    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(@RequestBody ProfileDTO profileDTO) {
        try {
            User currentUser = securityUtil.getCurrentUser();
            if (currentUser == null) {
                return ResponseEntity.status(401).body("User not authenticated");
            }

            // Update user fields
            currentUser.setName(profileDTO.getName());
            currentUser.setLocation(profileDTO.getLocation());
            currentUser.setProfilePhotoUrl(profileDTO.getProfilePhotoUrl());
            currentUser.setSkillsOffered(profileDTO.getSkillsOffered());
            currentUser.setSkillsWanted(profileDTO.getSkillsWanted());
            currentUser.setAvailability(profileDTO.getAvailability());
            currentUser.setPublic(profileDTO.isPublic());

            // Save updated user
            User updatedUser = userService.save(currentUser);

            // Return updated profile
            ProfileDTO updatedProfileDTO = new ProfileDTO();
            updatedProfileDTO.setName(updatedUser.getName());
            updatedProfileDTO.setLocation(updatedUser.getLocation());
            updatedProfileDTO.setProfilePhotoUrl(updatedUser.getProfilePhotoUrl());
            updatedProfileDTO.setSkillsOffered(updatedUser.getSkillsOffered());
            updatedProfileDTO.setSkillsWanted(updatedUser.getSkillsWanted());
            updatedProfileDTO.setAvailability(updatedUser.getAvailability());
            updatedProfileDTO.setPublic(updatedUser.isPublic());

            return ResponseEntity.ok(updatedProfileDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating profile: " + e.getMessage());
        }
    }

    // Get public profiles (for browsing)
    @GetMapping("/public")
    public ResponseEntity<?> getPublicProfiles() {
        try {
            return ResponseEntity.ok(userService.getPublicUsers());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching public profiles: " + e.getMessage());
        }
    }

    // Get user profile by ID (public)
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            if (!user.isPublic()) {
                return ResponseEntity.status(403).body("Profile is private");
            }

            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setName(user.getName());
            profileDTO.setLocation(user.getLocation());
            profileDTO.setProfilePhotoUrl(user.getProfilePhotoUrl());
            profileDTO.setSkillsOffered(user.getSkillsOffered());
            profileDTO.setSkillsWanted(user.getSkillsWanted());
            profileDTO.setAvailability(user.getAvailability());
            profileDTO.setPublic(user.isPublic());

            return ResponseEntity.ok(profileDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching user profile: " + e.getMessage());
        }
    }
} 