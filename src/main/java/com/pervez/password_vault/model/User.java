package com.pervez.password_vault.model;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String masterPasswordHash;

    @Column(nullable = false)
    private String salt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMasterPasswordHash() { return masterPasswordHash; }
    public void setMasterPasswordHash(String hash) { this.masterPasswordHash = hash; }

    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }
}