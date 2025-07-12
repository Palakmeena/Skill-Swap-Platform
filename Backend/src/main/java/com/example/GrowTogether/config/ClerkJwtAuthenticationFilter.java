package com.example.GrowTogether.config;

import com.example.GrowTogether.entity.User;
import com.example.GrowTogether.service.ClerkJwtService;
import com.example.GrowTogether.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class ClerkJwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private ClerkJwtService clerkJwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            String token = extractTokenFromRequest(request);
            
            if (token != null) {
                // Validate the JWT and extract user info
                String clerkId = clerkJwtService.extractClerkId(token);
                String email = clerkJwtService.extractEmail(token);
                String name = clerkJwtService.extractName(token);
                
                // Find or create user
                User user = userService.findByClerkId(clerkId);
                if (user == null) {
                    // Create new user from Clerk data
                    user = new User();
                    user.setClerkId(clerkId);
                    user.setEmail(email);
                    user.setName(name);
                    userService.save(user);
                }
                
                // Create authentication token
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, new ArrayList<>()
                );
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Log the error but don't throw it to allow the request to continue
            logger.error("Error processing JWT token", e);
        }
        
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
} 