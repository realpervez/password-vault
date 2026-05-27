package com.pervez.password_vault.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_entries")
public class PasswordEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String siteName;

    private String siteUrl;

    @Column(nullable = false)
    private String usernameForSite;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = false)
    private String iv;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }

    public String getSiteUrl() { return siteUrl; }
    public void setSiteUrl(String siteUrl) { this.siteUrl = siteUrl; }

    public String getUsernameForSite() { return usernameForSite; }
    public void setUsernameForSite(String usernameForSite) { this.usernameForSite = usernameForSite; }

    public String getEncryptedPassword() { return encryptedPassword; }
    public void setEncryptedPassword(String encryptedPassword) { this.encryptedPassword = encryptedPassword; }

    public String getIv() { return iv; }
    public void setIv(String iv) { this.iv = iv; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}