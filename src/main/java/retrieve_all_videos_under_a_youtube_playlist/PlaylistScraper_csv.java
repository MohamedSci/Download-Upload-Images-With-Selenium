package retrieve_all_videos_under_a_youtube_playlist;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;



import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;


public class PlaylistScraper_csv  extends TestBase{


	@Test
	public static void PlaylistScraperTest() throws IOException, ParseException, InterruptedException {		// Setup Selenium WebDriver
		
		String playlistName = 
				 "مغامرات التواصل: رحلتك الشخصية نحو التواصل الفعّال مع الجنس الأخر"
				; // Replace with the playlist name (e.g., extract it from playlist link)
//		String playlistLink = "https://www.youtube.com/watch?v=d1QJOiSuoh8&list=PLLw9vZtSqHMJdoHMGC4blGu2GlLo5BqbU"; 
			
		String playlistLink ="https://www.youtube.com/watch?list=PLLw9vZtSqHMJdoHMGC4blGu2GlLo5BqbU";
		driver.get(playlistLink);
			
			//div[5]/div[2]/div/ytd-playlist-panel-renderer/div/div[3]//a[@id="wc-endpoint"]
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
					By.xpath("//div[5]/div[2]/div/ytd-playlist-panel-renderer/div/div[3]//a[@id=\"wc-endpoint\"]")));
			List<WebElement> videoElements = driver.findElements(
					By.xpath("//div[5]/div[2]/div/ytd-playlist-panel-renderer/div/div[3]//a[@id=\"wc-endpoint\"]"));
			System.out.println("--- videoElements size: " + videoElements.size());
			
			//div[@id="thumbnail-container"]/ytd-thumbnail/a[@id="thumbnail"]
			//div[@id="thumbnail-container"]/ytd-thumbnail/a[@id="thumbnail"]/yt-image/img
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
					By.xpath("//div[@id=\"thumbnail-container\"]/ytd-thumbnail/a[@id=\"thumbnail\"]/yt-image/img")));
			List<WebElement> imagesElements = driver
					.findElements(By.xpath(
							"//div[@id=\"thumbnail-container\"]/ytd-thumbnail/a[@id=\"thumbnail\"]/yt-image/img"));
			System.out.println("--- imagesElements size: " + imagesElements.size());

			//div/h4/span[@id="video-title"]
			wait.until(
					ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div/h4/span[@id=\"video-title\"]")));
			List<WebElement> titlesElements = driver.findElements(By.xpath("//div/h4/span[@id=\"video-title\"]"));
			System.out.println("--- titlesElements size: " + titlesElements.size());

			JSONArray videoIds = new JSONArray();
			for (int i = 0; i < videoElements.size(); i++) {
				System.out.println("---For loop " );

				String href = videoElements.get(i).getAttribute("href");
				System.out.println("--- href: " + href);
				if (href != null && href.contains("watch")) {
					System.out.println("---If If If " );
					String videoId = href.substring(href.indexOf("=") + 1);
					System.out.println("--- videoId: " + videoId);
					String imgThumbnil = imagesElements.get(i).getAttribute("src");
					System.out.println("--- imgThumbnil: " + imgThumbnil);
					String videoTitle = titlesElements.get(i).getText();
					System.out.println("--- videoTitle: " + videoTitle);
					String textOutput = videoId + "*****" + imgThumbnil + "*****" + videoTitle;
					System.out.println("--- textOutput: " + textOutput);
					String filePath = "C:\\Users\\moham\\OneDrive\\Desktop\\true_love\\podcast_video_ids.csv";
					System.out.println("--- filePath 1111: " + filePath);

					AppendTextToFile.AppendTextToFileFun(textOutput, filePath);
					System.out.println("--- filePath 2222: " + filePath);

				}
			}


		// Close Selenium WebDriver
		driver.quit();
}
}
