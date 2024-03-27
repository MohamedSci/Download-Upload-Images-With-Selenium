package Maodoaa_Scarpping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

public class MaodoaScarpping extends TestBase {

	@Test
	public static void PlaylistScraperTest() throws IOException, ParseException, InterruptedException { // Setup
																										// Selenium
																										// WebDriver
		String outputFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\true_love\\loveMsgs.csv";
																									
		String modoaaLink = "https://mawdoo3.com/%D8%B1%D8%B3%D8%A7%D8%A6%D9%84_%D8%AD%D8%A8_%D9%82%D8%B5%D9%8A%D8%B1%D8%A9";
		driver.get(modoaaLink);
		// h2/span/b
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//h2/span/b")));
		List<WebElement> headerTitlesElements = driver.findElements(By.xpath("//h2/span/b"));
		System.out.println("--- headerTitlesElements size: " + headerTitlesElements.size());

		// p/strong
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//p/strong")));
		List<WebElement> subHeaderElements = driver.findElements(By.xpath("//p/strong"));
		System.out.println("--- subHeaderElements size: " + subHeaderElements.size());

		// ul/li
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul/li")));
		List<WebElement> messagesListElements = driver.findElements(By.xpath("//ul/li"));
		System.out.println("--- messagesListElements size: " + messagesListElements.size());

		JSONArray videoIds = new JSONArray();
		for (int i = 0; i < headerTitlesElements.size(); i++) {
			System.out.println("---For loop ");

			String headerTitle = headerTitlesElements.get(i).getText();
			String headerSubitle = subHeaderElements.get(i).getText();
			WebElement msgMenyList = messagesListElements.get(i);
			List<WebElement> msgsList = msgMenyList.findElements(By.xpath("//ul/child::li"));
			for (int c = 1; c < msgsList.size(); c++) {
				String msg = msgsList.get(c).getText();
				String textOutput = headerTitle +"*****" + msg;

				AppendTextToFile.AppendTextToFileFun(textOutput, outputFilePath);

			}
//if (href != null && href.contains("watch")) {
//	System.out.println("---If If If ");
//	String videoId = href.substring(href.indexOf("=") + 1);
//	System.out.println("--- videoId: " + videoId);
//	String imgThumbnil = imagesElements.get(i).getAttribute("src");
//	System.out.println("--- imgThumbnil: " + imgThumbnil);
//	String videoTitle = titlesElements.get(i).getText();
//	System.out.println("--- videoTitle: " + videoTitle);
//	String textOutput = videoId + "*****" + imgThumbnil + "*****" + videoTitle;
//	System.out.println("--- textOutput: " + textOutput);
//	String filePath = "C:\\Users\\moham\\OneDrive\\Desktop\\true_love\\podcast_video_ids.csv";
//	System.out.println("--- filePath 1111: " + filePath);
//
//	AppendTextToFile.AppendTextToFileFun(textOutput, filePath);
//	System.out.println("--- filePath 2222: " + filePath);
//
//}
		}

		// Close Selenium WebDriver
		driver.quit();
	}
}
