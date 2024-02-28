package generate_keys_from_map_list;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




public class generate_keys_from_map_list {

    public static void main(String[] args) {
        String jsonFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\single_zekr_dataset.json"; // Path to your JSON file
        String csvFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\single_zekr_dataset_keys.csv"; // Path to your output CSV file

        try {
            // Read JSON file content
            String jsonData = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

            // Parse JSON data
            JSONArray jsonArray = new JSONArray(jsonData);

            // Create list to store keys
            List<String> keysList = new ArrayList<>();

            // Iterate over JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Iterator<String> keys = jsonObject.keys();

                // Get keys from each JSON object and add to list
                while (keys.hasNext()) {
                    String key = keys.next();
                    keysList.add(key);
                }
            }

            // Write keys to CSV file
            writeKeysToCsv(keysList, csvFilePath);

            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeKeysToCsv(List<String> keysList, String csvFilePath) throws IOException {
        FileWriter writer = new FileWriter(csvFilePath);
        for (String key : keysList) {
            writer.write(key + "\n");
        }
        writer.close();
    }
}
