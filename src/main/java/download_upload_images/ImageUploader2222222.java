package download_upload_images;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ImageUploader2222222 {

	static WebDriver driver;
	static WebDriverWait wait;
	public static String downloadPath = System.getProperty("user.dir") + "\\Downloads";
	static String directoryPath = "D:\\celebrities_photos\\celebrities_photos_images";
	static int labelIndex = 1;

	public static ChromeOptions chromeOption() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
		chromeOptions.setExperimentalOption("useAutomationExtension", false);
		chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
//		chromeOptions.addArguments("--headless");
		return chromeOptions;
	}

	public static void setupdriver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(chromeOption());
		driver.get("https://teachablemachine.withgoogle.com/train/image");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
	}

	@Test
	public static void ImageUploaderTest() {
		setupdriver();

		// Navigate through directories
		File directory = new File(directoryPath);
		if (directory.exists() && directory.isDirectory()) {
			for (File celebrityDirectory : directory.listFiles()) {
				if (celebrityDirectory.isDirectory()) {
					String celebrityName = celebrityDirectory.getName();
//					List<WebElement> add_classes = driver.findElements(By.className("add-classes"));
//					add_classes.get(1).click();
					List<WebElement> labelContainerIds = driver.findElements(By.id("label-container"));
					WebElement labelContainer = labelContainerIds.get(labelIndex);
					// Locate the upload button
					labelContainer.click();
					labelContainer.clear();
					labelContainer.sendKeys(celebrityName);
					labelContainer.sendKeys(Keys.ENTER);
					List<WebElement> sampleSourceBtn = driver.findElements(By.className("sample-source-btn"));
					sampleSourceBtn.get(1).click();
			        // Wait for the upload file input element to be clickable
			        wait.until(ExpectedConditions.elementToBeClickable(By.id("upload-file")));
					WebElement uploadFile = driver.findElement(By.id("upload-file"));

					// Upload all JPEG images under the current celebrity directory
					for (File photo : celebrityDirectory.listFiles()) {
						if (photo.isFile() && photo.getName().endsWith(".jpg")) {
							// Set the absolute path of the photo
							String photoPath = photo.getAbsolutePath();
							// Upload the photo
							uploadFile.sendKeys(photoPath);
						}
					}
					// Label the uploaded images with the celebrity name
//                    WebElement labelInput = driver.findElement(By.xpath("//div[@class='label-name-container']//input"));
//                    labelInput.sendKeys(celebrityName);
//                    labelInput.submit();
					labelIndex = labelIndex + 2;
				}
			}
		} else {
			System.out.println("Directory not found or is not a directory.");
		}

		// Wait for model training and export (you may need to implement a wait
		// mechanism here)
		// Export the model
		WebElement exportButton = driver.findElement(By.xpath("//button[contains(text(),'Export Model')]"));
		exportButton.click();

		// Download the model
		// This step might require manual intervention or using another automation tool
		// You may need to inspect the webpage to find the download link and automate
		// the download process.

		// Close the browser
		driver.quit();
	}
}
