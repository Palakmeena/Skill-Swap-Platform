package com.example.GrowTogether.controller;

import com.example.GrowTogether.entity.User;
import com.example.GrowTogether.service.ClerkJwtService;
import com.example.GrowTogether.service.UserService;
import com.example.GrowTogether.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ClerkJwtService clerkJwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userMap) {
        String name = userMap.get("name");
        String email = userMap.get("email");
        String password = passwordEncoder.encode(userMap.get("password"));

        if (userService.findByEmail(email) != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userService.save(user); // you'll need to add this in service + repo

        return ResponseEntity.ok("User registered successfully");
    }

    // ✅ Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginMap) {
        String email = loginMap.get("email");
        String password = loginMap.get("password");

        User user = userService.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId().toString());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getId());
        response.put("name", user.getName());

        return ResponseEntity.ok(response);
    }

    // ✅ Clerk Authentication
    @PostMapping("/clerk")
    public ResponseEntity<?> clerkAuth(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Invalid authorization header");
            }

            String token = authHeader.substring(7);
            
            // Validate JWT and extract user info
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
                user = userService.save(user);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getId());
            response.put("clerkId", user.getClerkId());
            response.put("email", user.getEmail());
            response.put("name", user.getName());
            response.put("message", "Authentication successful");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token: " + e.getMessage());
        }
    }

    // ✅ Get current user info
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Invalid authorization header");
            }

            String token = authHeader.substring(7);
            String clerkId = clerkJwtService.extractClerkId(token);
            
            User user = userService.findByClerkId(clerkId);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getId());
            response.put("clerkId", user.getClerkId());
            response.put("email", user.getEmail());
            response.put("name", user.getName());
            response.put("location", user.getLocation());
            response.put("profilePhotoUrl", user.getProfilePhotoUrl());
            response.put("availability", user.getAvailability());
            response.put("isPublic", user.isPublic());
            response.put("skillsOffered", user.getSkillsOffered());
            response.put("skillsWanted", user.getSkillsWanted());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token: " + e.getMessage());
        }
    }
}