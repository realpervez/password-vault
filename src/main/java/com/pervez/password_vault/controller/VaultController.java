package com.pervez.password_vault.controller;

import com.pervez.password_vault.model.PasswordEntry;
import com.pervez.password_vault.model.User;
import com.pervez.password_vault.service.AuthService;
import com.pervez.password_vault.service.VaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vault")
public class VaultController {

    @Autowired
    private VaultService vaultService;

    @Autowired
    private AuthService authService;

    @PostMapping("/add")
    public ResponseEntity<?> addEntry(@RequestBody Map<String, String> request) {
        try {
            User user = authService.login(
                    request.get("username"),
                    request.get("masterPassword")
            );
            PasswordEntry entry = vaultService.addEntry(
                    user,
                    request.get("siteName"),
                    request.get("siteUrl"),
                    request.get("usernameForSite"),
                    request.get("plainPassword"),
                    request.get("masterPassword")
            );
            return ResponseEntity.ok("Password saved with ID: " + entry.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listEntries(@RequestParam String username,
                                         @RequestParam String masterPassword) {
        try {
            User user = authService.login(username, masterPassword);
            List<PasswordEntry> entries = vaultService.getAllEntries(user);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/decrypt/{id}")
    public ResponseEntity<?> decryptEntry(@PathVariable Long id,
                                          @RequestParam String username,
                                          @RequestParam String masterPassword) {
        try {
            User user = authService.login(username, masterPassword);
            String plainPassword = vaultService.decryptEntry(user, id, masterPassword);
            return ResponseEntity.ok("Decrypted password: " + plainPassword);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long id,
                                         @RequestBody Map<String, String> request) {
        try {
            User user = authService.login(
                    request.get("username"),
                    request.get("masterPassword")
            );
            vaultService.deleteEntry(user, id);
            return ResponseEntity.ok("Entry deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEntries(@RequestParam String username,
                                           @RequestParam String masterPassword,
                                           @RequestParam String siteName) {
        try {
            User user = authService.login(username, masterPassword);
            List<PasswordEntry> entries = vaultService.searchEntries(user, siteName);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}