package com.pervez.password_vault.service;

import com.pervez.password_vault.model.User;
import com.pervez.password_vault.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptionService encryptionService;

    public User register(String username, String email, String masterPassword) throws Exception {

        // Check if username already taken
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email already taken
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        // Generate unique salt for this user
        String salt = encryptionService.generateSalt();

        // Hash the master password
        String passwordHash = encryptionService.hashPassword(masterPassword, salt);

        // Create and save the user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setMasterPasswordHash(passwordHash);
        user.setSalt(salt);

        return userRepository.save(user);
    }

    public User login(String username, String masterPassword) throws Exception {

        // Find user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify master password
        boolean valid = encryptionService.verifyPassword(
                masterPassword,
                user.getMasterPasswordHash(),
                user.getSalt()
        );

        if (!valid) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}