
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

// class handle aes enc and dec
public class AESEncryptionDemo {
    private SecretKey sKey; // holds key for enc

    // generating aes keyy
    public void generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        // Setting common key size 128 bits
        keyGenerator.init(128);

        // Generate the sk and store it in the secretKe
        sKey = keyGenerator.generateKey();
    }

    // encrypt a plain text string
    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");

        // Initializing the cipher in ENCRYPT_MODE using our sk
        cipher.init(Cipher.ENCRYPT_MODE, sKey);

        // Encrypting plain text string by converting into to bytess
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        // Convert the encrypted bytes into a Base64 encoded string
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // decrypt an encrypted text string
    public String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");

        // Initializing the cipher in DECRYPT_MODE using our sk
        cipher.init(Cipher.DECRYPT_MODE, sKey);

        // Decode the Base64 encoded string back into bytes and decrypt them
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

        // Convert the decrypted bytes into a normal readable string and returning it
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try {
            AESEncryptionDemo aes = new AESEncryptionDemo();

            // Generate and initialize the aes key before we encrypt or decrypt anything
            aes.generateKey();
            String originalText = "Renaissance";

            // Encrypt text using our encrypt method
            String encryptedText = aes.encrypt(originalText);

            // Print encrypted text (in Base644 format)
            System.out.println("Encrypted Text: " + encryptedText);

            // Decrypting it to original text
            String decryptedText = aes.decrypt(encryptedText);

            // Printing devrypted
            System.out.println("Decrypted Text: " + decryptedText);

        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur during execution (for debugging)
        }
    }
}