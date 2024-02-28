package replace_key_in_json;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class ReplaceKeysWithTranslations {

    public static void main(String[] args) {
        String inputJsonFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\single_zekr_dataset_key_grouping.json"; // Path to your input JSON file
        String csvFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\zekr_titles_english_arabic.csv"; // Path to your CSV file containing new keys
        String outputJsonFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\single_zekr_dataset_key_grouping_arabic.json"; // Path to your output JSON file
      	
          
        try {
            // Read JSON file content
            String jsonData = new String(Files.readAllBytes(Paths.get(inputJsonFilePath)));

            // Parse JSON data
            JSONObject jsonObject = new JSONObject(jsonData);

            // Read CSV file to get translations
            Map<String, String> translations = readTranslationsFromCsv(csvFilePath);

            // Replace keys in JSON
            JSONObject modifiedJson = replaceKeysWithTranslations(jsonObject, translations);

            // Write modified JSON to file
            writeJsonToFile(modifiedJson, outputJsonFilePath);

            System.out.println("New JSON file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> readTranslationsFromCsv(String filePath) throws FileNotFoundException {
        Map<String, String> translations = new HashMap<>();
        Scanner scanner = new Scanner(new File(filePath));
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(",");
            if (line.length == 2) {
                translations.put(line[0].trim(), line[1].trim());
            }
        }
        scanner.close();
        return translations;
    }

    private static JSONObject replaceKeysWithTranslations(JSONObject jsonObject, Map<String, String> translations) {
        JSONObject modifiedJson = new JSONObject();
        for (String key : jsonObject.keySet()) {
            String translatedKey = translations.getOrDefault(key, key);
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                modifiedJson.put(translatedKey, replaceKeysWithTranslations((JSONObject) value, translations));
            } else {
                modifiedJson.put(translatedKey, value);
            }
        }
        return modifiedJson;
    }

    private static void writeJsonToFile(JSONObject jsonObject, String filePath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toString(4)); // Indentation of 4 spaces
        }
    }
}




//public class ReplaceKeysWithTranslations {
//
//    public static void main(String[] args) {
//    	
//      String inputJsonFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\single_zekr_dataset_key_grouping.json"; // Path to your input JSON file
//      String csvFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\zekr_titles_english_arabic.csv"; // Path to your CSV file containing new keys
//      String outputJsonFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\single_zekr_dataset_key_grouping_arabic.json"; // Path to your output JSON file
//    	
//        
//
//        try {
//            // Read JSON file content
//            String jsonData = new String(Files.readAllBytes(Paths.get(inputJsonFilePath)));
//
//            // Parse JSON data
//            JSONObject jsonObject = new JSONObject(jsonData);
//
//            // Read CSV file to get translations
//            Map<String, String> translations = readTranslationsFromCsv(csvFilePath);
//
//            // Replace keys in JSON
//            JSONObject modifiedJson = replaceKeysWithTranslations(jsonObject, translations);
//
//            // Write modified JSON to file
//            writeJsonToFile(modifiedJson, outputJsonFilePath);
//
//            System.out.println("New JSON file created successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static Map<String, String> readTranslationsFromCsv(String filePath) throws FileNotFoundException {
//        Map<String, String> translations = new HashMap<>();
//        Scanner scanner = new Scanner(new File(filePath));
//        while (scanner.hasNextLine()) {
//            String[] line = scanner.nextLine().split(",");
//            if (line.length == 2) {
//                translations.put(line[0].trim(), line[1].trim());
//            }
//        }
//        scanner.close();
//        return translations;
//    }
//
//    private static JSONObject replaceKeysWithTranslations(JSONObject jsonObject, Map<String, String> translations) {
//        JSONObject modifiedJson = new JSONObject();
//        JSONArray jsonArray = jsonObject.toJSONArray(jsonObject.names());
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONArray entry = jsonArray.getJSONArray(i);
//            String originalKey = entry.getString(0);
//            String translatedKey = translations.getOrDefault(originalKey, originalKey);
//            modifiedJson.put(translatedKey, entry.get(1));
//        }
//        return modifiedJson;
//    }
//
//    private static void writeJsonToFile(JSONObject jsonObject, String filePath) throws IOException {
//        try (FileWriter fileWriter = new FileWriter(filePath)) {
//            fileWriter.write(jsonObject.toString(4)); // Indentation of 4 spaces
//        }
//    }
//}






//public class ReplaceKeysInJson {
//
//    public static void main(String[] args) {
//        String inputJsonFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\single_zekr_dataset_key_grouping.json"; // Path to your input JSON file
//        String csvFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\zekr_titles_arabic.csv"; // Path to your CSV file containing new keys
//        String outputJsonFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\single_zekr_dataset_key_grouping_arabic.json"; // Path to your output JSON file
//
//        try {
//            // Read JSON file content
//            String jsonData = new String(Files.readAllBytes(Paths.get(inputJsonFilePath)));
//
//            // Parse JSON data
//            JSONObject jsonObject = new JSONObject(jsonData);
//
//            // Read CSV file to get new keys
//            String[] newKeys = readCsv(csvFilePath);
//
//            // Replace keys in JSON
//            JSONObject modifiedJson = replaceKeys(jsonObject, newKeys);
//
//            // Write modified JSON to file
//            writeJsonToFile(modifiedJson, outputJsonFilePath);
//
//            System.out.println("New JSON file created successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static String[] readCsv(String filePath) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File(filePath));
//        StringBuilder sb = new StringBuilder();
//        while (scanner.hasNextLine()) {
//            sb.append(scanner.nextLine()).append(",");
//        }
//        scanner.close();
//        return sb.toString().split(",");
//    }
//
//    private static JSONObject replaceKeys(JSONObject jsonObject, String[] newKeys) {
//        JSONObject modifiedJson = new JSONObject();
//        JSONArray jsonArray = jsonObject.toJSONArray(jsonObject.names());
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONArray entry = jsonArray.getJSONArray(i);
//            String key = newKeys[i];
//            modifiedJson.put(key, entry);
//        }
//        return modifiedJson;
//    }
//
//    private static void writeJsonToFile(JSONObject jsonObject, String filePath) throws IOException {
//        try (FileWriter fileWriter = new FileWriter(filePath)) {
//            fileWriter.write(jsonObject.toString(4)); // Indentation of 4 spaces
//        }
//    }
//}
//
