package mostajaab_scrapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AppendMapResourceToJson {

    public static void AppendMapResourceToJsonFuv(String key, ArrayList<HashMap<String, String>> value) {
        String fileName = "doaa_data.json";
        JSONObject newMapResource = new JSONObject();
        newMapResource.put(key, value);

        try {
            JSONObject jsonObject;
            // Read existing JSON file content, if it exists
            try (FileReader reader = new FileReader(fileName)) {
                // Parse the JSON content into a JSON object
                JSONParser jsonParser = new JSONParser();
                jsonObject = (JSONObject) jsonParser.parse(reader);
            } catch (IOException | ParseException e) {
                // File does not exist or cannot be parsed, create a new JSON object
                jsonObject = new JSONObject();
            }
            // Add the new map resource to the JSON object
            JSONArray existingMapResources = (JSONArray) jsonObject.getOrDefault("mapResources", new JSONArray());
            existingMapResources.add(newMapResource);
            jsonObject.put("mapResources", existingMapResources);
            // Convert the JSON object back to a string
            String jsonString = jsonObject.toJSONString();
            // Write the updated JSON content back to the file
            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(jsonString);
            }
            System.out.println("Map resource appended to JSON file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to JSON file: " + e.getMessage());
        }
    }
}

