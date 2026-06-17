package com.pervez.password_vault.dto;

import com.pervez.password_vault.model.PasswordEntry;
import java.time.LocalDateTime;

public class PasswordEntryResponse {

    private Long id;
    private String siteName;
    private String siteUrl;
    private String usernameForSite;
    private LocalDateTime createdAt;

    public PasswordEntryResponse(PasswordEntry entry) {
        this.id = entry.getId();
        this.siteName = entry.getSiteName();
        this.siteUrl = entry.getSiteUrl();
        this.usernameForSite = entry.getUsernameForSite();
        this.createdAt = entry.getCreatedAt();
    }

    // Getters
    public Long getId() { return id; }
    public String getSiteName() { return siteName; }
    public String getSiteUrl() { return siteUrl; }
    public String getUsernameForSite() { return usernameForSite; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}