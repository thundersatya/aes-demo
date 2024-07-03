import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESDecryptionExample {
    public static void main(String[] args) throws Exception {
        String encryptedBase64 = "YourBase64EncryptedDataWithIV";
        String key = "YourBase64EncodedKey";
        
        // Decode the base64 encoded key
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");

        byte[] encryptedWithIv = Base64.getDecoder().decode(encryptedBase64);
        
        // Extract the IV
        byte[] iv = new byte[16];
        System.arraycopy(encryptedWithIv, 0, iv, 0, iv.length);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        
        // Extract the encrypted data
        byte[] encrypted = new byte[encryptedWithIv.length - iv.length];
        System.arraycopy(encryptedWithIv, iv.length, encrypted, 0, encrypted.length);
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        byte[] decrypted = cipher.doFinal(encrypted);
        String decryptedText = new String(decrypted, "UTF-8");

        System.out.println("Decrypted Data: " + decryptedText);
    }
}
