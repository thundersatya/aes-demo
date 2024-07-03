package com.satya.service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AesEncryption {
    public static void main(String[] args) {
        try {

            String hexKey = "3b0f123f5a678c9c6b2934508f59a8a3a3e2fdb9188a1ecf7a8eaf8eec01f4d2";

            // Convert hex string to byte array
            byte[] keyBytes = hexStringToByteArray(hexKey);

            // Create a SecretKeySpec from the byte array
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

            // Generate a random IV
            byte[] iv = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // The message to encrypt
            String message = "sample key";

            // Perform encryption
            String encryptedMessage = encrypt(message, secretKey, ivParameterSpec);
            System.out.println("Encrypted Message: " + encryptedMessage);

            // Perform decryption
            String decryptedMessage = decrypt(encryptedMessage, secretKey, ivParameterSpec);
            System.out.println("Decrypted Message: " + decryptedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to encrypt a message
    public static String encrypt(String message, SecretKeySpec secretKey, IvParameterSpec ivParameterSpec) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] messageBytes = message.getBytes("UTF-8");
        byte[] encryptedBytes = cipher.doFinal(messageBytes);
        return Base64.getEncoder().encodeToString(encryptedBytes) + ":" + Base64.getEncoder().encodeToString(ivParameterSpec.getIV());
    }

    // Method to decrypt a message
    public static String decrypt(String encryptedMessage, SecretKeySpec secretKey, IvParameterSpec ivParameterSpec) throws Exception {
        String[] parts = encryptedMessage.split(":");
        byte[] encryptedBytes = Base64.getDecoder().decode(parts[0]);
        byte[] ivBytes = Base64.getDecoder().decode(parts[1]);
        IvParameterSpec newIvParameterSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, newIvParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, "UTF-8");
    }

    // Utility method to convert hex string to byte array
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
