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
	ArrayList<String> doaaDescriptionList = new ArrayList<String>();
	AppendMapResourceToJson appendMapResourceToJson = new AppendMapResourceToJson();
	HashMap<String, String> mainMap = new HashMap<String, String>();
	HashMap<String, ArrayList<HashMap<String, String>>> mapCategory = new HashMap<String, ArrayList<HashMap<String, String>>>();

	void operateFun(String category) {
		CraateDoaListsFun(category);
		ArrayList<HashMap<String, String>> mapCategoryList = new ArrayList<HashMap<String, String>>();
		for (int s = 0; s < doaaDescriptionList.size(); s++) {
			HashMap<String, String> map0 = new HashMap<String, String>();
			map0.put(doaaTitleList.get(s), doaaDescriptionList.get(s));
			mapCategoryList.add(map0);
		}
		appendMapResourceToJson.AppendMapResourceToJsonFuv(category, mapCategoryList);
	}

	void CraateDoaListsFun(String category) {
		// div[@id='main-articles-holder']/div/div/div/div
		String commonElementXpath = "//div[@id='main-articles-holder']/div/div/div/div";
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(commonElementXpath)));
		List<WebElement> commonElementList = driver.findElements(By.xpath(commonElementXpath));
		for (int e = 0; e < commonElementList.size(); e++) {
			WebElement commonElement = commonElementList.get(e);
			String titleXpath = "//div[@id='main-articles-holder']/div/div/div/div/a/h2";
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(titleXpath)));
			List<WebElement> titleElementList = commonElement.findElements(By.xpath(titleXpath));

			String descriptionXpath = "//div[@id='main-articles-holder']/div/div/div/div/div/div/div/p";
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(descriptionXpath)));
			List<WebElement> descriptionElementsList = commonElement.findElements(By.xpath(descriptionXpath));
			System.out.println("---titleElementList Size: " + titleElementList.size());
			System.out.println("---doaaDescription Size: " + descriptionElementsList.size());
			System.out.println("---doaaTitle *****************************");
			for (WebElement titleElement : titleElementList) {
				String doaaTitle = titleElement.getText();
				if (doaaTitle.contains("اللهم")) {
					continue;
				}
				doaaTitleList.add(category + "--" + doaaTitle);
			}
			System.out.println("---doaaDescription *****************************");
			for (WebElement descElement : descriptionElementsList) {
				String doaaDescription = descElement.getText();
				doaaDescriptionList.add(doaaDescription);
			}
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

	@Test
	public void getDoaaDataTest() {
		getMenuLinksFun();
		for (int i = 0; i < menuLinksList.size(); i++) {
			driver.get(menuLinksList.get(i));
			String category0 = menuLinksList.get(i);
			String category = category0.split("/c/").toString();
			operateFun(category);

		}

	}

}
