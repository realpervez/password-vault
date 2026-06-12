package com.pervez.password_vault.controller;

import com.pervez.password_vault.model.User;
import com.pervez.password_vault.security.JwtUtil;
import com.pervez.password_vault.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String email = request.get("email");
            String masterPassword = request.get("masterPassword");

            User user = authService.register(username, email, masterPassword);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String masterPassword = request.get("masterPassword");

            User user = authService.login(username, masterPassword);
            String token = jwtUtil.generateToken(username);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("username", user.getUsername());
            response.put("token", token);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}