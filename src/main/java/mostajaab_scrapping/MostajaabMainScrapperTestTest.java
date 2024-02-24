package mostajaab_scrapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class MostajaabMainScrapperTestTest extends BasedClass {

	String baseUrl = "https://mostajaab.com";
	ArrayList<String> menuLinksList = new ArrayList<String>();
	ArrayList<String> doaaTitleList = new ArrayList<String>();
	AppendMapResourceToJson appendMapResourceToJson = new AppendMapResourceToJson();
	HashMap<String, String> mainMap = new HashMap<String, String>();
	HashMap<String, ArrayList<HashMap<String, String>>> mapCategory = new HashMap<String, ArrayList<HashMap<String, String>>>();

	@Test
	public void getDoaaDataTest() {
		getMenuLinksFun();
		for (int i = 0; i < menuLinksList.size(); i++) {
			driver.get(menuLinksList.get(i));
			String cat0 = menuLinksList.get(i);
			String category = cat0.split("/c/")[1].toString();
			ArrayList<String> doaaDescriptionList = new ArrayList<String>();

			String descriptionXpath = "//div[@id='main-articles-holder']/div/div/div/div/div/div/div/p";
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(descriptionXpath)));
			List<WebElement> descriptionElementsList = driver.findElements(By.xpath(descriptionXpath));
			System.out.println("---doaaDescription Size: " + descriptionElementsList.size());
			for (WebElement descElement : descriptionElementsList) {
				String doaaDescription = descElement.getText();
				doaaDescriptionList.add(doaaDescription);
			}						
			AppendMapResourceToJson.AppendMapResourceToJsonFuv(category, doaaDescriptionList);
		}
	}
	
	
	
	public void getMenuLinksFun() {
		// ul[@id="navigation"]/child::li/a
		String Main_menu_Items_Xpath = "//ul[@id=\"navigation\"]/child::li/a";
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(Main_menu_Items_Xpath)));
		List<WebElement> Main_menu_Items = driver.findElements(By.xpath(Main_menu_Items_Xpath));
		for (WebElement menu_Item : Main_menu_Items) {
			if (menu_Item != Main_menu_Items.get(0)) {
				String menuLink = menu_Item.getAttribute("href");
				System.out.println("---MostajaabMainScrapperTest menuLink: " + menuLink);
				menuLinksList.add(menuLink);
			}
		}
	}



}
