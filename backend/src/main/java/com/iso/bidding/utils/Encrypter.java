package com.iso.bidding.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Encrypter {

    public static String encryptPassword(String password) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Compute the hash of the password
            byte[] hash = md.digest(password.getBytes());

            // Convert the hash to a hexadecimal string representation
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
