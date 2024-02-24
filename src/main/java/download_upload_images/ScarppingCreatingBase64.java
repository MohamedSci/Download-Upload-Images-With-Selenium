package download_upload_images;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ScarppingCreatingBase64 {
	WebDriver driver;
	WebDriverWait wait;
	public static String downloadPath = System.getProperty("user.dir") + "\\Downloads";
	String parentbase64FolderPath = "D:\\celebrities_photos\\celebrities_photos_base64";
	String csvFilePath = "D:\\celebrities_photos\\celebrities_vertical_2.csv";
	String[] headings = { "Japan", "Black African Countries", "East Europe", "Egypt", "Gulf countries", "USA", "Uk",
			"Germany", "India", "China", "Italy", "France", "Spain", "North Europe", "Korea", "Russia",
			"South East Asia" };

	public void saveCelebrityBase64ImageToFile(String imgPath, String imageBase64) {
		// Save The Celebrity Base64 Image into file
		File outputFile = new File(imgPath);
		FileWriter writer;
		try {
			writer = new FileWriter(outputFile);
			writer.write(imageBase64);
			writer.close();
		} catch (IOException e) {
			System.out.println("--- saveCelebrityBase64ImageToFile Exception " + e.getMessage());
		}
	}

	public void createAreaFolders() {
		File parentFolder = new File(parentbase64FolderPath);
		if (!parentFolder.exists()) {
			parentFolder.mkdirs();
		}
		for (String areaFolderName : headings) {
			File areaParentFolder = new File(parentbase64FolderPath + "/" + areaFolderName);
			if (!areaParentFolder.exists()) {
				areaParentFolder.mkdir();
			}
		}
	}

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

	public void setupDriver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(chromeOption());
		driver.get("https://www.google.com/imghp");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
	}

	public void setupLicenceAndSize(WebDriver driver) {
		// Tools Button on Main menue
		driver.findElement(By.xpath("//body[@id='yDmH0d']/div[2]/c-wiz/div/div/div/div[2]/div[2]/div")).click();
		// Choose Licences
		driver.findElement(
				By.xpath("//body[@id='yDmH0d']/div[2]/c-wiz/div[2]/div[2]/c-wiz/div/div/div/div/div[5]/div/div"))
				.click();
		// Creative Licences
		driver.findElement(
				By.xpath("//body[@id='yDmH0d']/div[2]/c-wiz/div[2]/div[2]/c-wiz/div/div/div[3]/div/a[2]/div")).click();

		// Choose Image Size
		driver.findElement(
				By.xpath("//body[@id='yDmH0d']/div[2]/c-wiz/div[2]/div[2]/c-wiz/div/div/div/div/div/div/div")).click();
		// Middle Size Image
		driver.findElement(
				By.xpath("//body[@id='yDmH0d']/div[2]/c-wiz/div[2]/div[2]/c-wiz/div/div/div[3]/div/a[3]/div/span"))
				.click();

	}

	public void make_action(WebDriver driver, WebDriverWait wait, String celebrityName, String celbrity_folder_path) {
		// Input the celebrity name in the search bar and press Enter
		List<WebElement> imageElements = null;
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.clear();
		searchBox.sendKeys(celebrityName);
		searchBox.submit();
		// setupLicenceAndSize(driver);
		try {
			// Wait for the images to load
			String imgXPath = "//a[@role=\"button\"]/div/img";
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(imgXPath)));
			// Get all the image elements
			imageElements = driver.findElements(By.xpath(imgXPath));
			if (imageElements.size() > 0) {
				// Download the first 10 images
				for (int i = 0; i < Math.min(10, imageElements.size()); i++) {
					String imageBase64String = imageElements.get(i).getAttribute("src");
					System.out
							.println("--- make_action_Download_Image_imageUrl ..." + imageBase64String + " ---------");
					// Code for downloading image goes here
					String imgPath = celbrity_folder_path + "\\" + celebrityName + String.valueOf(i) + ".txt";
					// Save The Celebrity Base64 Image into file
					saveCelebrityBase64ImageToFile(imgPath, imageBase64String);
				}
			}
		} catch (Exception e) {
			driver.quit();
			System.out.println("---make_action imageElements " + e.getMessage());
			setupDriver();
		}

	}

	@Test
	public void Base64ImagesCreatorTest() throws InterruptedException {
		createAreaFolders();

		setupDriver();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
			String line;

			while ((line = br.readLine()) != null) {

				// Reset BufferedReader to start reading from the beginning of the file (skip
				// first line)
				br.mark(1);
				br.reset();
				String[] data = line.split(",");
				String celibrityName = data[1];
				System.out.println("---  celibrityName " + celibrityName);
				String celibrityArea = data[2];
				System.out.println("---  celibrityArea " + celibrityArea);
				if (celibrityName == "Name") {
					continue;
				}
				File areaParentFolder = new File(parentbase64FolderPath + "/" + celibrityArea);
				if (!areaParentFolder.exists()) {
					areaParentFolder.mkdir();
				}
				String celebrityParentFolderPath = parentbase64FolderPath + "/" + celibrityArea + "/" + celibrityName;
				System.out.println("---  celebrityParentFolderPath " + celebrityParentFolderPath);
				File subFolder = new File(celebrityParentFolderPath);
				if (!subFolder.exists()) {
					subFolder.mkdir();
				}
				make_action(driver, wait, celibrityName, celebrityParentFolderPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
