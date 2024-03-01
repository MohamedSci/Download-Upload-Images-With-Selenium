package get_ayah_no_in_all_quran;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AppendTextToFile {

    public static void AppendTextToFileFun(String textToAppend) {
        String fileName = "C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\get_total_ayah.txt"; // Name of the file to append to

        try {
            // FileWriter with append mode set to true
            FileWriter fileWriter = new FileWriter(fileName, true);

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

