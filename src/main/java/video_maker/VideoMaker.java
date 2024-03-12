package video_maker;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class VideoMaker {

    public static void main(String[] args) {
        try {
            // Read text file
            List<String> texts = Files.readAllLines(Paths.get("text_file.txt"));

            // Parse CSV file with audio links
            CSVParser csvParser = CSVParser.parse(new File("audio_links.csv"), 
                                                  java.nio.charset.StandardCharsets.UTF_8, 
                                                  CSVFormat.DEFAULT);
            List<CSVRecord> records = csvParser.getRecords();

            // Process each line
            for (int i = 0; i < texts.size() && i < records.size(); i++) {
                String text = texts.get(i);
                String audioLink = records.get(i).get(0);

                // Download audio file
                String audioFilePath = downloadFile(audioLink, "audio_" + i + ".mp3");

                // Get audio duration
                long audioDuration = getAudioDuration(audioFilePath);

                // Create video using FFmpeg
                String videoFilePath = "video_" + i + ".mp4";
                createVideo(audioFilePath, text, audioDuration, videoFilePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Download audio file from URL
    private static String downloadFile(String url, String fileName) throws IOException {
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(fileName));
            return fileName;
        }
    }

    // Get audio duration using FFmpeg
    private static long getAudioDuration(String audioFilePath) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("ffprobe", "-v", "error", "-show_entries",
                "format=duration", "-of", "default=noprint_wrappers=1:nokey=1", audioFilePath);
        Process p = pb.start();
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        InputStream is = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String durationString = reader.readLine();
        return (long) Double.parseDouble(durationString);
    }

    // Create video using FFmpeg
    private static void createVideo(String audioFilePath, String text, long audioDuration, String videoFilePath)
            throws IOException {
        // Command to create video using FFmpeg
        String[] cmd = {"ffmpeg", "-loop", "1", "-i", "background_image.jpg", "-i", audioFilePath, "-c:v", "libx264",
                        "-t", String.valueOf(audioDuration), "-pix_fmt", "yuv420p", "-vf", "drawtext=text='" + text + 
                        "':fontcolor=white:fontsize=24:x=(w-text_w)/2:y=(h-text_h)/2", "-shortest", videoFilePath};

        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process p = pb.start();
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
