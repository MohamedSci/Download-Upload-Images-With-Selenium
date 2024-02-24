package retrieve_all_videos_under_a_youtube_playlist;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PlaylistScraper {

	static WebDriver driver;
	static WebDriverWait wait;
	public static String downloadPath = System.getProperty("user.dir") + "\\Downloads";
	ObjectMapper objectMapper = new ObjectMapper();
	String jsonFilePath = "C:\\Users\\moham\\eclipse-workspace\\download_upload_images\\celebrities_data.json";

	public static ChromeOptions chromeOption() {
		ChromeOptions chromeOptions = new ChromeOptions();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default.content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadPath);
		chromeOptions.setExperimentalOption("prefs", chromePrefs);
		chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		chromeOptions.addArguments("--headless");
		return chromeOptions;
	}

	@BeforeSuite
	public void setupDriver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(chromeOption());
		driver.manage().window().maximize();
		driver.get("https://www.youtube.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 60);
	}

	@Test
	public static void PlaylistScraperTest() throws IOException, ParseException {
		// Setup Selenium WebDriver
		driver.get("https://www.youtube.com/");

		// Read CSV file
		BufferedReader csvReader = new BufferedReader(new FileReader("Islamic_playlists.csv"));
		String row;
		Map<String, String> playlistMap = new HashMap<>();
		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(",");
			playlistMap.put(data[0], data[1]);
		}
		csvReader.close();

		// Take YouTube playlist link from the user
		String playlistName = ""; // Replace with the playlist name (e.g., extract it from playlist link)
		String playlistLink = ""; // Replace with your code to take input from the user
		for (String key : playlistMap.keySet()) {
			playlistName = key;
			playlistLink = playlistMap.get(key);

			// Get all video IDs under the playlist
			//div[5]/div[2]/div/ytd-playlist-panel-renderer/div/div[3]//a[@id="wc-endpoint"]
			driver.get(playlistLink);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[5]/div[2]/div/ytd-playlist-panel-renderer/div/div[3]//a[@id=\"wc-endpoint\"]")));
			List<WebElement> videoElements = driver.findElements(By.xpath("//div[5]/div[2]/div/ytd-playlist-panel-renderer/div/div[3]//a[@id=\"wc-endpoint\"]"));
			System.out.println("--- videoElements size: "+videoElements.size());

			JSONArray videoIds = new JSONArray();
			for (WebElement element : videoElements) {
				String href = element.getAttribute("href");
				System.out.println("--- href: "+href);
				if (href != null && href.contains("watch")) {
					String videoId = href.substring(href.indexOf("=") + 1);
					System.out.println("--- videoId: "+videoId);
					videoIds.add(videoId);
				}
			}
			// Create a map for the playlist and its video IDs
			Map<String, JSONArray> playlistVideosMap = new HashMap<>();
			System.out.println("--- videoIdsssssssssss: "+videoIds);
			System.out.println("--- playlistName: "+playlistName);

			playlistVideosMap.put(playlistName, videoIds);

			// Read existing JSON file if it exists
			JSONArray existingPlaylists;
			File jsonFile = new File("new_playlists.json");
			if (jsonFile.exists()) {
				JSONParser parser = new JSONParser();
				existingPlaylists = (JSONArray) parser.parse(new FileReader(jsonFile));
			} else {
				existingPlaylists = new JSONArray();
			}
			// Append the new playlist map to existing JSON content
			existingPlaylists.add(playlistVideosMap);
			// Write the updated JSON to file
			try (FileWriter fileWriter = new FileWriter("new_playlists.json")) {
				fileWriter.write(existingPlaylists.toJSONString());
			}
		}

		// Close Selenium WebDriver
		driver.quit();
	}
}
