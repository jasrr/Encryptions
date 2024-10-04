import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection connection; // Database connection

    public DatabaseManager(Connection connection) {
        this.connection = connection; // Initialize the database connection
    }

    // Simple XOR Encryption/Decryption
    private String xorEncryptDecrypt(String data, char key) {
        StringBuilder result = new StringBuilder(); // Result
        
        // Iterate over each character in the input data
        for (char c : data.toCharArray()) {
            result.append((char) (c ^ key)); // XOR each character with the key
        }
        
        return result.toString(); // Return encrypted or decrypted string
    }

    // Encrypt path of video before storing it in the database
    private String encryptVideoPathXor(String videoPath) {
        char key = 'K'; // Simple key for XOR encryption
        return xorEncryptDecrypt(videoPath, key); // Encrypt the video path
    }

    // Decrypt video path after getting it from the database
    private String decryptVideoPathXor(String encryptedPath) {
        char key = 'K'; // Using the same key for decryption
        return xorEncryptDecrypt(encryptedPath, key); // Decrypt video path
    }

    // Fetch ordered videos from the database
    public List<String> getOrderedVideos() throws SQLException {
        List<String> decryptedPaths = new ArrayList<>(); // List to hold decrypted paths
        
        String query = "SELECT file_path FROM video_chunks ORDER BY id ASC"; // SQL query

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String encryptedPath = rs.getString("file_path"); // Get encrypted path from result set
                String decryptedPath = decryptVideoPathXor(encryptedPath); // Decrypt the path
                decryptedPaths.add(decryptedPath); // Add to list of decrypted paths
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            throw e; // Rethrow exception for further handling if needed
        }

        return decryptedPaths; // Return list of decrypted paths
    }
}