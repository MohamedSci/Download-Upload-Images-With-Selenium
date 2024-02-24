package teachable_machine;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;



public class TeachableMachineImageUploader {

	WebDriver driver;
	WebDriverWait wait;
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
//		chromeOptions.addArguments("--headless");
		return chromeOptions;
	}
	
	@BeforeClass
	public void prepareClass() {
		driver.get("https://teachablemachine.withgoogle.com/train/image");
	}

	@Test
	public void TeachableMachineImageUploaderTest() throws InterruptedException {
        try {
            // Read JSON file
            JsonNode rootNode = objectMapper.readTree(new File(jsonFilePath));
            // Iterate through each JSON object
            Iterator<String> fieldNames = rootNode.fieldNames();
            while (fieldNames.hasNext()) {
                String celebrity_name = fieldNames.next();
                JsonNode valueNode = rootNode.get(celebrity_name);
                // Extracting values as list of Strings
                List<String> photoPaths = new ArrayList<>();
                if (valueNode.isArray()) {
                    for (JsonNode node : valueNode) {
                    	photoPaths.add(node.asText());
                    }
                }
        		// Wait for the page to load
    			Thread.sleep(5000);
    			// Click "Add a class" button to create a new class
    			WebElement addClassButton = driver.findElement(By.xpath("//button[contains(text(), 'Add a class')]"));
    			addClassButton.click();
    			// Find the input field for the class label and input celebrity_name
    			WebElement classLabelInput = driver.findElement(By.xpath("//input[@aria-label='Class label']"));
    			classLabelInput.sendKeys();
    			// Find the upload button
    			WebElement uploadButton = driver.findElement(By.xpath("//button[@aria-label='Upload']"));
    			// Loop through each photo path and upload the photo
    			for (String photoPath : photoPaths) {
    				// Click the upload button
    				uploadButton.click();
    				// Find the "Choose images from your files" button
    				WebElement chooseImageButton = driver.findElement(By.xpath("//input[@type='file']"));
    				// Send the photo path to the file input
    				chooseImageButton.sendKeys(photoPath);
    				// Wait for the upload to complete (you may need to adjust this wait time)
    				Thread.sleep(3000);
    			}
                
                System.out.println("--- Key: " + celebrity_name);
                System.out.println("--- Value List: " + photoPaths);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }		
	}
	

}
