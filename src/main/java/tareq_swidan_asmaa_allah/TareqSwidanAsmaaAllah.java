package tareq_swidan_asmaa_allah;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;


public class TareqSwidanAsmaaAllah extends BasedClass{
	List<WebElement> titleElements;
	List<WebElement> descElements;
	AppendMapResourceToJson appendMapResourceToJson = new AppendMapResourceToJson();
	public void getScreenElementsList(){
		//div[@class="col-12 col-lg-8 content step-up"]/ol/li/strong/a
		String titleXpath ="//div[@class=\"col-12 col-lg-8 content step-up\"]/ol/li/strong/a";
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(titleXpath)));
		titleElements = driver.findElements(By.xpath(titleXpath));
		//div[@class="col-12 col-lg-8 content step-up"]/ol/li
		String descXpath ="//div[@class=\"col-12 col-lg-8 content step-up\"]/ol/li";
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(descXpath)));
		descElements = driver.findElements(By.xpath(descXpath));
	}
	
	@Test
	public void createKeyValueMap(){
		getScreenElementsList();
		System.out.println("---getScreenElementsList titleElements.size() "+titleElements.size());
		System.out.println("---getScreenElementsList descElements.size() "+descElements.size());

		for(int s=0;s<= descElements.size();s++) {
			String desc =descElements.get(s).getText();
			AppendMapResourceToJson.AppendMapResourceToJsonFuv(s+1, desc);
			
		}
		
	}
	

}
