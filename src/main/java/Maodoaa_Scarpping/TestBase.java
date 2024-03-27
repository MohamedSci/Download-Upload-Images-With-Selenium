package Maodoaa_Scarpping;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;



public class TestBase {

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
		driver.get("https://mawdoo3.com/%D8%B1%D8%B3%D8%A7%D8%A6%D9%84_%D8%AD%D8%A8_%D9%82%D8%B5%D9%8A%D8%B1%D8%A9");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 60);
	}
	
	

}
