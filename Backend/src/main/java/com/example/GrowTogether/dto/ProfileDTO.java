package com.example.GrowTogether.dto;

import java.util.List;

public class ProfileDTO {
    private String name;
    private String location;
    private String profilePhotoUrl;
    private List<String> skillsOffered;
    private List<String> skillsWanted;
    private String availability;
    private boolean isPublic;

    // Constructors
    public ProfileDTO() {}

    public ProfileDTO(String name, String location, String profilePhotoUrl, 
                     List<String> skillsOffered, List<String> skillsWanted, 
                     String availability, boolean isPublic) {
        this.name = name;
        this.location = location;
        this.profilePhotoUrl = profilePhotoUrl;
        this.skillsOffered = skillsOffered;
        this.skillsWanted = skillsWanted;
        this.availability = availability;
        this.isPublic = isPublic;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getProfilePhotoUrl() { return profilePhotoUrl; }
    public void setProfilePhotoUrl(String profilePhotoUrl) { this.profilePhotoUrl = profilePhotoUrl; }

    public List<String> getSkillsOffered() { return skillsOffered; }
    public void setSkillsOffered(List<String> skillsOffered) { this.skillsOffered = skillsOffered; }

    public List<String> getSkillsWanted() { return skillsWanted; }
    public void setSkillsWanted(List<String> skillsWanted) { this.skillsWanted = skillsWanted; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }

    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean aPublic) { isPublic = aPublic; }
} 