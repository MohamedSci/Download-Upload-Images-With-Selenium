package download_upload_images;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.testng.annotations.Test;

public class CleanBase64Data {
	
	@Test
    public  void CleanBase64DataTest() {
        // Path to the directory containing the text files
        String directoryPath = "D:\\celebrities_photos\\celebrities_photos_base64";
        try {
            // Iterate through each text file in the directory
            Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            cleanBase64Data(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cleanBase64Data(Path filePath) throws IOException {
        // Read the contents of the text file
        String base64Data;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            base64Data = reader.readLine();
        }
        // Extract only the base64-encoded data (remove any additional characters or metadata)
        String[] parts = base64Data.split(",");
        String cleanBase64Data = parts.length > 1 ? parts[1] : parts[0];
        // Write the cleaned base64 data back to the text file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write(cleanBase64Data);
        }
    }
}
