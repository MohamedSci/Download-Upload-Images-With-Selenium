//package download_upload_images;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.PageLoadStrategy;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.remote.CapabilityType;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.annotations.Test;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//
//import java.io.BufferedReader;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//public class CelebrityImageDownloader {
//
//	
//	public static String downloadPath = System.getProperty("user.dir") + "\\Downloads";
//
//	public static ChromeOptions chromeOption() {
//		ChromeOptions chromeOptions = new ChromeOptions();
//		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
//		chromePrefs.put("profile.default.content_settings.popups", 0);
//		chromePrefs.put("download.default_directory", downloadPath);
//		chromeOptions.setExperimentalOption("prefs", chromePrefs);
//		chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//		chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
////		chromeOptions.addArguments("--headless");
//		return chromeOptions;
//	}
//
//	@Test
//	public void main() throws InterruptedException {
//		WebDriverManager.chromedriver().setup();
//		WebDriver driver = new ChromeDriver(chromeOption());
//		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		WebDriverWait wait = new WebDriverWait(driver, 5);
//
//        // Read celebrities' names from CSV file
//        try (BufferedReader br = new BufferedReader(new FileReader("celebrities.csv"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                // Split the line to get celebrity name
//                String[] parts = line.split(",");
//                String celebrityName = parts[0].trim();
//                // Create a folder with the celebrity name
//                String folderPath = "D:\\celebrities_photos\\" + celebrityName;
//                createFolder(folderPath);
//                // Navigate to Google Images
//                driver.get("https://www.google.com/imghp");
//                // Input the celebrity name in the search bar and press Enter
//                WebElement searchBox = driver.findElement(By.name("q"));
//                searchBox.clear();
//                searchBox.sendKeys(celebrityName);
//                searchBox.submit();
//                // Click on Tools button to display filter options
//                WebElement toolsButton = driver.findElement(By.xpath("//div[@aria-label='Tools']"));
//                toolsButton.click();
//                // Click on Size dropdown and select Small option
//                WebElement sizeDropdown = driver.findElement(By.xpath("//div[text()='Size']"));
//                sizeDropdown.click();
//                WebElement smallSizeOption = driver.findElement(By.xpath("//a[text()='Small']"));
//                smallSizeOption.click();
//                // Wait for the images to load
//                Thread.sleep(2000);
//                // Get all the image elements
//                List<WebElement> imageElements = driver.findElements(By.tagName("img"));
//                // Download the first 10 images
//                for (int i = 0; i < Math.min(10, imageElements.size()); i++) {
//                    String imageUrl = imageElements.get(i).getAttribute("src");
//                    // Download the image and save it to the celebrity's folder
//                    // Code for downloading image goes here
//                    downloadImage(imageUrl,"D:\\celebrities_photos\\"+celebrityName);
//                }
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Close the browser
//        driver.quit();
//    }
//
//    private static void createFolder(String folderPath) throws IOException {
//        Path path = Paths.get(folderPath);
//        if (!Files.exists(path)) {
//            Files.createDirectories(path);
//        }
//    }
//
//    private static void downloadImage(String imageUrl, String folderPath) throws IOException {
//        // Extract the image name from the URL
//        String[] parts = imageUrl.split("/");
//        String imageName = parts[parts.length - 1];
//        // Construct the local file path
//        String localFilePath = folderPath + "/" + imageName;
//        // Connect to the URL and get the input stream
//        URL url = new URL(imageUrl);
//        InputStream in = url.openStream();
//        // Open the output stream and save the image
//        FileOutputStream out = new FileOutputStream(localFilePath);
//        byte[] buffer = new byte[1024];
//        int bytesRead;
//        while ((bytesRead = in.read(buffer)) != -1) {
//            out.write(buffer, 0, bytesRead);
//        }
//
//        // Close streams
//        out.close();
//        in.close();
//    }
//}
//
//
