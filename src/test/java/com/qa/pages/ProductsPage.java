package com.qa.pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;

import com.qa.BaseTest;
import com.qa.MenuPage;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;


public class ProductsPage extends MenuPage {
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView") 
	@iOSXCUITFindBy (xpath = "//XCUIElementTypeStaticText[@name=\"PRODUCTS\"]")
	private WebElement productTitleTxt;
	
	@AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
	@iOSXCUITFindBy (xpath = "(//XCUIElementTypeStaticText[@name=\"test-Item title\"])[1]")
	private WebElement SLBTitle;
	
	@AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
	@iOSXCUITFindBy (xpath = "(//XCUIElementTypeStaticText[@name=\"test-Price\"])[1]")
	private WebElement SLBprice;


	public String getTitle() {
		String title = getText(productTitleTxt, "product page title is - ");
		return title;
	}
	public String getSLBTitle() {
		String title = getText(SLBTitle, "title is - ");
		return title;
	}

	public String getSLBPrice() {
		String price = getText(SLBprice, "price is - ");
		return price;
	}
	public ProductDetailsPage pressSLBTitle() {
		click(SLBTitle, "press SLB tile link");
		return new ProductDetailsPage();
	}


/*
 * public ProductsPage pressBackToProductBtn() {
 * System.out.println("navigate back to products page "); click
 * (backToProductBtn); return new ProductsPage(); }
 */


/*
 * //IOS public SettingsPage pressSettingsBtn() {
 * System.out.println("press Settings button"); WebElement element =
 * driver.findElement(AppiumBy.accessibilityId("test-Menu"));
 * 
 * Map<String, Object> params = new HashMap<>(); params.put("x", 49);
 * params.put("y", 68); driver.executeScript("mobile: tap", params); return new
 * SettingsPage(); }
 */
}
