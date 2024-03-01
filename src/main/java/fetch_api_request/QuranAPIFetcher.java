package fetch_api_request;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class QuranAPIFetcher {
	public static void main(String[] args) {
		try {
			// Open the CSV file
			File csvFile = new File("doaa_ayahs_quran.csv");
			Scanner scanner = new Scanner(csvFile);

			// Create or append to the output text file
			FileWriter fileWriter = new FileWriter("doaa_ayahs_quran_texts_dataset.txt", true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			int index = 1;
			// Iterate through each line in the CSV file
			while (scanner.hasNextLine()) {
				String ayahNo = scanner.nextLine().trim();
				String apiUrl = "https://api.alquran.cloud/v1/ayah/" + ayahNo;

				// Make HTTP GET request
				URL url = new URL(apiUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");

				// Read the response
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();

				// Parse JSON response
				String jsonResponse = response.toString();
				// Assuming you have a JSON parser, parse the response and get the text
//                 For example, if using org.json.JSONObject:
				JSONObject jsonObject = new JSONObject(jsonResponse);
				String text = jsonObject.getJSONObject("data").getString("text");
				String outPutText = index + "," + ayahNo + "," + text;
				// Write the text to the output file
				printWriter.println(outPutText);
				index++;

                // Close the connection
                connection.disconnect();
            }

            // Close the scanner and print writer
            scanner.close();
            printWriter.close();

            System.out.println("Data fetched successfully and written to output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
