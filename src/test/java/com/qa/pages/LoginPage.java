package com.qa.pages;

import com.qa.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.qa.BaseTest;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;


public class LoginPage extends BaseTest {
	@AndroidFindBy(accessibility = "test-Username")
	@iOSXCUITFindBy(id = "test-Username")
	private WebElement usernameTxFld;


	@AndroidFindBy(accessibility = "test-Password")
	@iOSXCUITFindBy(id = "test-Password")
	private WebElement passwordFld;

	@AndroidFindBy(accessibility = "test-LOGIN")
	@iOSXCUITFindBy(id = "test-LOGIN")
	private WebElement loginButton;


	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Error message\"]/child::XCUIElementTypeStaticText")
	private WebElement errTxt;


	public LoginPage enterUserName(String username) {
		clear(usernameTxFld);
		sendKeys(usernameTxFld, username, "login with " + username);
		return this;
	}

	public LoginPage enterPassword(String password) {
		clear(passwordFld);
		sendKeys(passwordFld, password, "password is " + password);
		return this;
	}

	public ProductsPage pressLoginBtn() {
		click(loginButton, "press login button");
		return new ProductsPage();
	}

	public ProductsPage login(String username, String password) {
		enterUserName(username);
		enterPassword(password);
		return pressLoginBtn();
	}

	public String getErrTxt() {
		String err = getText(errTxt, "error text is - ");
		return err;
	}
}
