package islam_can_stories_scrapping;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class IslamCanScrapperTestTest extends BasedClass {
	String csvLinksFile = "islamic_stories_links.csv";
	String baseUrl = "https://islamcan.com";

//	void getMainLink() {
//		List<WebElement> storyLinksList = driver
//				.findElements(By.xpath("//div[@class=\"w3-twothird w3-container\"]/ul/li/a"));
//		for (WebElement webElement : storyLinksList) {
//			String link = baseUrl + webElement.getAttribute("href").toString();
//			AppendTextToFile.AppendTextToFileFun(link,csvLinksFile);
//		}
//	}
//
//	@Test
//	public void assignStoriesLinksTOTextFile() throws InterruptedException {
//		driver.get("https://islamcan.com/increaseiman/index.shtml");
//		driver.navigate().refresh();
//		Thread.sleep(1000);
//		getMainLink();
//		Thread.sleep(1000);
//		for (int i = 1; i < 14; i++) {
//			String pageLink = "https://islamcan.com/increaseiman/islamic-stories-page-" + String.valueOf(1+i) + ".shtml";
//			driver.get(pageLink);
//			driver.navigate().refresh();
//			Thread.sleep(1000);
//			getMainLink();
//		}
//	}

	void getMainStoryData() {
		WebElement storyLinkHeader = driver.findElement(By.xpath("//div[@class=\"w3-twothird w3-container\"]/h1"));
		WebElement storyLinkBody = driver.findElement(By.xpath("//div[@class=\"w3-twothird w3-container\"]/p"));

		String storyParagraph = storyLinkHeader.getText() + "\n\n\n" + storyLinkBody.getText();
		AppendTextToFile.AppendTextToFileFun(storyParagraph, storyLinkHeader.getText());
	}

	@Test
	public void assignStoriesDataTOTextFile() throws InterruptedException {
		String csvFilePath = "C:\\Users\\moham\\OneDrive\\Desktop\\islamic_stories\\islamic_stories_links.csv";
        try {
        	Thread.sleep(2500);
            CSVReader csvReader = new CSVReader(new FileReader(csvFilePath));
        	Thread.sleep(2500);

            String[] row;
            while ((row = csvReader.readNext()) != null) {
                System.out.println("---- assignStoriesDataTOTextFile csvReader row: "+row[0]);
                System.out.println("---- assignStoriesDataTOTextFile csvReader row.toString().trim(): "+row[0].toString().trim());
            	Thread.sleep(500);

                driver.get(row[0].toString().trim());
                Thread.sleep(1000);
                driver.navigate().refresh();
                Thread.sleep(500);
                getMainStoryData();
                Thread.sleep(2000);
            }

            csvReader = new CSVReader(new FileReader(csvFilePath)); // Reset CSV reader
            csvReader.readNext();       
                csvReader.close();
            

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
