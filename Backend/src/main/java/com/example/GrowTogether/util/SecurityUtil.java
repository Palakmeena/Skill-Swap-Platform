package com.example.GrowTogether.util;

import com.example.GrowTogether.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    public String getCurrentUserId() {
        User user = getCurrentUser();
        return user != null ? user.getId().toString() : null;
    }

    public String getCurrentUserClerkId() {
        User user = getCurrentUser();
        return user != null ? user.getClerkId() : null;
    }
} 