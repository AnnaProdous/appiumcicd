package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.support.PageFactory;
import com.qa.MenuPage;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;



public class SettingsPage extends BaseTest {
	
	@AndroidFindBy (accessibility = "test-LOGOUT")
	@iOSXCUITFindBy (id = "test-LOGOUT")
	private WebElement logoutBtn;

	BaseTest base;
	public LoginPage pressLogoutBtn() {
		System.out.println("press Logout button");
		click(logoutBtn);
		return new LoginPage();
}
	public SettingsPage() {
		base = new BaseTest();
		PageFactory.initElements(new AppiumFieldDecorator(base.getDriver()), this);
	}
}
