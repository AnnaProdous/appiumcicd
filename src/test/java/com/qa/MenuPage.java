package com.qa;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.qa.pages.SettingsPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.util.HashMap;
import java.util.Map;

public class MenuPage extends BaseTest{
	TestUtils utils = new TestUtils();
	JavascriptExecutor js = (JavascriptExecutor) driver.get();

@AndroidFindBy (accessibility = "test-Menu")
//@iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Menu\"]/XCUIElementTypeOther")
private WebElement settingsBtn;

/*//Android
public SettingsPage pressSettingsBtn() {
	click(settingsBtn, "press Settings button");
	return new SettingsPage();
}
*/

	//IOS
	public SettingsPage pressSettingsBtn() {
		System.out.println("press Settings button");
	//	AppiumDriver driverInstance = driver.get();
	//	WebElement element = driver.get().findElement(AppiumBy.accessibilityId("test-Menu"));

		JavascriptExecutor js = (JavascriptExecutor) driver.get();
		Map<String, Object> params = new HashMap<>();
		params.put("x", 49);
		params.put("y", 68);
		js.executeScript("mobile: tap", params);
		return new SettingsPage();
	}
}