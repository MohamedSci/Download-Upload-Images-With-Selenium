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
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ImageUploader11111 {
	
	static WebDriver driver;
	static WebDriverWait wait;
	public static String downloadPath = System.getProperty("user.dir") + "\\Downloads";

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
		driver.get("https://www.google.com/imghp");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
	}
	@Test
    public static void ImageUploaderTest() {

		setupdriver();
        // Open Google Teachable Machine website
        driver.get("https://teachablemachine.withgoogle.com/train/image");
        
        List<WebElement> dialogeExistBtn= driver.findElements(By.id("id=\"exit-container\""));
        if(dialogeExistBtn.size() >0) {
        	dialogeExistBtn.get(0).click();
        }
        // Locate the upload button
        WebElement uploadButton = driver.findElement(By.xpath("//input[@type='file']"));

        // Define the path to the directory containing celebrity photos
        String directoryPath = "D:\\celebrities_photos\\celebrities_photos_images";

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
                }
            }
        } else {
            System.out.println("Directory not found or is not a directory.");
        }

        // Close the browser
        driver.quit();
    }
}
