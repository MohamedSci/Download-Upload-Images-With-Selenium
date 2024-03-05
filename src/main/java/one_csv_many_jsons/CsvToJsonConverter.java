package one_csv_many_jsons;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvToJsonConverter {

    public static void main(String[] args) throws CsvValidationException {
        String csvFilePath = "muslim_app_widgets_translation.csv";
        try {
            CSVReader csvReader = new CSVReader(new FileReader(csvFilePath));
            String[] header = csvReader.readNext();
            if(header ==  null || header.length == 0){
            	System.out.println("---Error: header ==  null || header.length == 0");// Read header row
            }else {
                for (int i = 1; i < header.length; i++) {
                    Map<String, String> dataMap = new HashMap<>();
                    String outputFileName = "C:\\Users\\moham\\eclipse-workspace\\download_upload_images\\src\\main\\java\\one_csv_many_jsons\\output\\"+header[i] + ".json";
                    String[] row;
                    while ((row = csvReader.readNext()) != null) {
                        dataMap.put(row[0], row[i]);
                    }
                    writeJsonFile(dataMap, outputFileName);
                    csvReader = new CSVReader(new FileReader(csvFilePath)); // Reset CSV reader
                    csvReader.readNext(); // Skip header row
                }
                csvReader.close();
                System.out.println("JSON files generated successfully.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeJsonFile(Map<String, String> dataMap, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(dataMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

