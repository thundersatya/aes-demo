import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESExample {
    public static void main(String[] args) throws Exception {
        String plaintext = "YourDataToEncrypt";
        String key = "YourBase64EncodedKey";
        
        // Decode the base64 encoded key
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");

        // Generate random IV
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        byte[] encrypted = cipher.doFinal(plaintext.getBytes("UTF-8"));
        
        // Prepend IV to the encrypted data
        byte[] encryptedWithIv = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
        System.arraycopy(encrypted, 0, encryptedWithIv, iv.length, encrypted.length);

        String base64Encrypted = Base64.getEncoder().encodeToString(encryptedWithIv);

        System.out.println("Encrypted Data: " + base64Encrypted);
    }
}
