package com.example.our_ebd.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return hashWithSHA256(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equalsIgnoreCase(encodedPassword);
    }

    private String hashWithSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
