package org.example.commons.utilities.api;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public interface SecurityUtility {

    default String generateString() {
        String symbols = "abcdefghijklmnopqrstuvwxyzABCEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new SecureRandom();
        char[] buffer = new char[32];
        for(int idx = 0; idx < buffer.length; idx++) {
            buffer[idx] = symbols.charAt(random.nextInt(symbols.length()));
        }
        return new String(buffer);
    }

    default String generateKey() {
        String key = generateString();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(key.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception ex) {
            return "";
        }

    }
}
