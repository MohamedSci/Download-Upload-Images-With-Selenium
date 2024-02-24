package download_upload_images;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.testng.annotations.Test;

public class RefracoringGeneratedJpgImagesFolders {
@Test
    public void FileManipulationSecTest() {
        // Specify the path to the ancestor folder
        String ancestorFolderPath = "D:\\celebrities_photos\\celebrities_photos_images";
        // Specify the path to the new ancestor folder
        String treatedFolderPath = "D:/celebrities_treated";

        // Create the treated folder if it doesn't exist
        File treatedFolder = new File(treatedFolderPath);
        if (!treatedFolder.exists()) {
            treatedFolder.mkdir();
        }

        // Loop through country folders
        File ancestorFolder = new File(ancestorFolderPath);
        File[] countryFolders = ancestorFolder.listFiles(File::isDirectory);
        if (countryFolders != null) {
            for (File countryFolder : countryFolders) {
                // Loop through celebrity folders
                File[] celebrityFolders = countryFolder.listFiles(File::isDirectory);
                if (celebrityFolders != null) {
                    for (File celebrityFolder : celebrityFolders) {
                        // Create a new celebrity treated folder
                        String newCelebrityFolderPath = treatedFolderPath + "/" + celebrityFolder.getName();
                        File newCelebrityFolder = new File(newCelebrityFolderPath);
                        if (!newCelebrityFolder.exists()) {
                            newCelebrityFolder.mkdir();
                        }

                        // Loop through files in the celebrity folder
                        File[] files = celebrityFolder.listFiles();
                        if (files != null) {
                            for (File file : files) {
                                if (file.isFile() && file.getName().endsWith(".jpg")) {
                                    // Move .jpg files to the treated folder
                                    try {
                                        Files.move(file.toPath(), Path.of(newCelebrityFolderPath + "/" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Processing completed.");
    }
}
