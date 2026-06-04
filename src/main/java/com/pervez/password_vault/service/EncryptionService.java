package com.pervez.password_vault.service;

import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EncryptionService {

    private static final int ITERATIONS = 310000;
    private static final int KEY_LENGTH = 256;
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    // Generate a random salt for a new user
    public String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash the master password using PBKDF2
    public String hashPassword(String password, String saltBase64) throws Exception {
        byte[] salt = Base64.getDecoder().decode(saltBase64);
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(), salt, ITERATIONS, KEY_LENGTH
        );
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    // Verify master password during login
    public boolean verifyPassword(String inputPassword, String storedHash, String saltBase64) throws Exception {
        String inputHash = hashPassword(inputPassword, saltBase64);
        return inputHash.equals(storedHash);
    }

    // Derive AES encryption key from master password + salt
    private SecretKey deriveKey(String password, String saltBase64) throws Exception {
        byte[] salt = Base64.getDecoder().decode(saltBase64);
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(), salt, ITERATIONS, KEY_LENGTH
        );
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    // Encrypt a password before storing it
    public String encrypt(String plainText, String masterPassword, String saltBase64) throws Exception {
        SecretKey key = deriveKey(masterPassword, saltBase64);
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, iv));
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(iv) + ":" + Base64.getEncoder().encodeToString(encrypted);
    }

    // Decrypt a stored password for  display
    public String decrypt(String encryptedData, String masterPassword, String saltBase64) throws Exception {
        String[] parts = encryptedData.split(":");
        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] encrypted = Base64.getDecoder().decode(parts[1]);
        SecretKey key = deriveKey(masterPassword, saltBase64);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, iv));
        return new String(cipher.doFinal(encrypted));
    }
}