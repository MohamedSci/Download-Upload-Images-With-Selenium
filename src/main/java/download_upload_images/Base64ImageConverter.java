package download_upload_images;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.testng.annotations.Test;

public class Base64ImageConverter {
	@Test
    public void Base64ImageConverterTest() {
        // Specify the path to the parent folder
        String parentFolderPath = "D:\\celebrities_photos\\celebrities_photos_base64";
        // Traverse through the parent folder and its subfolders
        try {
            Files.walk(Paths.get(parentFolderPath))
                    .filter(Files::isRegularFile)
                    .forEach(Base64ImageConverter::convertBase64ToImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convertBase64ToImage(Path filePath) {
        try {
            // Read the contents of the text file
            String base64Data = Files.readString(filePath);
            // Decode the base64 data
            byte[] imageData = Base64.getDecoder().decode(base64Data);
            // Create a file name for the output JPEG image
            String outputFileName = filePath.toString().replace(".txt", ".jpg");
            // Write the decoded data to a JPEG image file
            try (FileOutputStream fos = new FileOutputStream(outputFileName)) {
                fos.write(imageData);
            }
            System.out.println("Converted " + filePath.getFileName() + " to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
