package com.qa;

import com.qa.utils.TestUtils;
import org.openqa.selenium.WebElement;

import com.qa.pages.SettingsPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class MenuPage extends BaseTest{
	TestUtils utils = new TestUtils();

@AndroidFindBy (accessibility = "test-Menu")
@iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Menu\"]/XCUIElementTypeOther")
private WebElement settingsBtn;

public SettingsPage pressSettingsBtn() {
	click(settingsBtn, "press Settings button");
	return new SettingsPage();
}
}