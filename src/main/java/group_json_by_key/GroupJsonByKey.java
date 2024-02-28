package group_json_by_key;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GroupJsonByKey {

    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\single_zekr_dataset.json"; // Path to your JSON file
        String outputFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\single_zekr_dataset_key_grouping.json"; // Path to your JSON file

        try {
            // Read JSON file content
            String jsonData = new String(Files.readAllBytes(Paths.get(inputFilePath)));

            // Parse JSON data
            JSONArray jsonArray = new JSONArray(jsonData);

            // Group entries by key
            JSONObject groupedJson = groupJsonByKey(jsonArray);

            // Write grouped JSON to file
            writeJsonToFile(groupedJson, outputFilePath);

            System.out.println("New JSON file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject groupJsonByKey(JSONArray jsonArray) {
        Map<String, JSONArray> groupedEntries = new HashMap<>();

        // Iterate over JSON array
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Iterator<String> keys = jsonObject.keys();

            // Iterate over keys of each JSON object
            while (keys.hasNext()) {
                String key = keys.next();
                JSONArray values = groupedEntries.getOrDefault(key, new JSONArray());
                values.put(jsonObject.get(key));
                groupedEntries.put(key, values);
            }
        }

        // Construct grouped JSON object
        JSONObject groupedJson = new JSONObject();
        for (String key : groupedEntries.keySet()) {
            groupedJson.put(key, groupedEntries.get(key));
        }

        return groupedJson;
    }

    private static void writeJsonToFile(JSONObject jsonObject, String filePath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toString(4)); // Indentation of 4 spaces
        }
    }
}

