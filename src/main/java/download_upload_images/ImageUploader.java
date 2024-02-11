package download_upload_images;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ImageUploader {

	static WebDriver driver;
	static WebDriverWait wait;
	public static String downloadPath = System.getProperty("user.dir") + "\\Downloads";
    static String directoryPath = "D:\\celebrities_photos\\celebrities_photos_images";

	public static ChromeOptions chromeOption() {
		ChromeOptions chromeOptions = new ChromeOptions();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default.content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadPath);
		chromeOptions.setExperimentalOption("prefs", chromePrefs);
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
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
	}
	@Test
    public static void ImageUploaderTest() {
		setupdriver();
        // Locate the upload button
        WebElement uploadButton = driver.findElement(By.xpath("//input[@type='file']"));

        // Navigate through directories
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            for (File celebrityDirectory : directory.listFiles()) {
                if (celebrityDirectory.isDirectory()) {
                    String celebrityName = celebrityDirectory.getName();

                    // Upload all JPEG images under the current celebrity directory
                    for (File photo : celebrityDirectory.listFiles()) {
                        if (photo.isFile() && photo.getName().endsWith(".jpg")) {
                            // Set the absolute path of the photo
                            String photoPath = photo.getAbsolutePath();
                            // Upload the photo
                            uploadButton.sendKeys(photoPath);
                        }
                    }
                    // Label the uploaded images with the celebrity name
                    WebElement labelInput = driver.findElement(By.xpath("//div[@class='label-name-container']//input"));
                    labelInput.sendKeys(celebrityName);
                    labelInput.submit();
                }
            }
        } else {
            System.out.println("Directory not found or is not a directory.");
        }

        // Wait for model training and export (you may need to implement a wait mechanism here)
        // Export the model
        WebElement exportButton = driver.findElement(By.xpath("//button[contains(text(),'Export Model')]"));
        exportButton.click();

        // Download the model
        // This step might require manual intervention or using another automation tool
        // You may need to inspect the webpage to find the download link and automate the download process.

        // Close the browser
        driver.quit();
    }
}
