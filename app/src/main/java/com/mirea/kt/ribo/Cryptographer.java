package com.mirea.kt.ribo;

public class Cryptographer {

    public static String encrypt(String password, String masterPassword) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            result.append((char) (password.charAt(i) ^ masterPassword.charAt(i % masterPassword.length())));
        }
        return result.toString();
    }

    public static String decrypt(String encryptedPassword, String masterPassword) {
        return encrypt(encryptedPassword, masterPassword);
    }
}