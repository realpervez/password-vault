package com.pervez.password_vault.repository;

import com.pervez.password_vault.model.PasswordEntry;
import com.pervez.password_vault.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PasswordEntryRepository extends JpaRepository<PasswordEntry, Long> {
    List<PasswordEntry> findByUser(User user);
    Optional<PasswordEntry> findByIdAndUser(Long id, User user);
    void deleteByIdAndUser(Long id, User user);
    List<PasswordEntry> findByUserAndSiteNameContainingIgnoreCase(User user, String siteName);
}
