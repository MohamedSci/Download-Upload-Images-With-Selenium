package retrieve_all_videos_under_a_youtube_playlist;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AppendTextToFile {

   public static void AppendTextToFileFun(String textToAppend, String filePath) {

        try {
            // FileWriter with append mode set to true
            FileWriter fileWriter = new FileWriter(filePath, true);

            // BufferedWriter for efficient writing
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

           

            // Append the text to the file
            bufferedWriter.write(textToAppend);
            bufferedWriter.newLine(); // Add newline for readability

            // Close the BufferedWriter
            bufferedWriter.close();

            System.out.println("Text appended to file successfully.");

        } catch (IOException e) {
            System.out.println("An error occurred while appending to the file.");
            e.printStackTrace();
        }
    }
}

