package download_upload_images;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.testng.annotations.Test;


public class MakeJsonforCelebrityAndRelatedPhotos {
	
    File ancestorDirectory = new File("D:\\celebrities_photos\\celebrities_photos_images");
    List<Map<String, List<String>>> listOfMaps = new ArrayList<>();
    
	@Test
    public  void CelebrityPhotosScraperTest() {
        // Loop through parent directories
        for (File parentDirectory : Objects.requireNonNull(ancestorDirectory.listFiles())) {
            if (parentDirectory.isDirectory()) {
                Map<String, List<String>> celebrityMap = new HashMap<>();
                List<String> photoPaths = new ArrayList<>();
                String celebrityName = "";
                // Loop through subfolders
                for (File subFolder : Objects.requireNonNull(parentDirectory.listFiles())) {
                    if (subFolder.isDirectory()) {
                        celebrityName = subFolder.getName();

                        // Loop through .jpg files
                        for (File jpgFile : Objects.requireNonNull(subFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg")))) {
                            photoPaths.add(jpgFile.getAbsolutePath());
                        }
                    }
                }
                // Add to the map
                celebrityMap.put(celebrityName, photoPaths);
                // Add the map to the list
                listOfMaps.add(celebrityMap);
            }
        }
        // Write the list of maps to a JSON file
        String jsonFilePath = "D:\\celebrities_photos\\celebrities_data.json";
        try {
            FileWriter fileWriter = new FileWriter(jsonFilePath);
            fileWriter.write(convertListOfMapsToJson(listOfMaps));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertListOfMapsToJson(List<Map<String, List<String>>> listOfMaps) {
        StringBuilder json = new StringBuilder("[");
        for (Map<String, List<String>> map : listOfMaps) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                json.append("{ \"").append(entry.getKey()).append("\": [");
                for (String photoPath : entry.getValue()) {
                    json.append("\"").append(photoPath).append("\",");
                }
                if (!entry.getValue().isEmpty()) {
                    json.deleteCharAt(json.length() - 1); // Remove the trailing comma
                }
                json.append("]},");
            }
        }
        if (!listOfMaps.isEmpty()) {
            json.deleteCharAt(json.length() - 1); // Remove the trailing comma
        }
        json.append("]");
        return json.toString();
    }
}
