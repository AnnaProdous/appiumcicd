package com.qa.tests;

import com.qa.utils.TestUtils;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import com.qa.reports.ExtentReport;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTests extends BaseTest {
	LoginPage loginPage;
	ProductsPage productsPage;
	JSONObject loginUsers;
	TestUtils utils = new TestUtils();

	@BeforeClass
	public void beforeClass() throws Exception {
		InputStream datais = null;
		try {
			String dataFileName = "data/loginUsers.json";
			datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			JSONTokener tokener = new JSONTokener(datais);
			loginUsers = new JSONObject(tokener);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(datais != null) {
				datais.close();
			}
		}
		closeApp();
		launchApp();
	}

	@AfterClass
	public void afterClass() {
	}

	@BeforeMethod
	public void beforeMethod(Method m) {
		utils.log().info("\n" + "****** starting test:" + m.getName() + "******" + "\n");
		loginPage = new LoginPage();
	}


 	 @AfterMethod
 	 public void afterMethod() {
  	}

	@Test
	public void invalidUserName() {
		loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
		loginPage.pressLoginBtn();

		String actualErrTxt = loginPage.getErrTxt();
		String expectedErrTxt = getStrings().get("err_invalid_username_or_password");

		Assert.assertEquals(actualErrTxt, expectedErrTxt);
	}
	  
	  @Test
	  public void invalidPassword() {
		  loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
		  loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
		  loginPage.pressLoginBtn();

		  String actualErrTxt = loginPage.getErrTxt();
		  String expectedErrTxt = getStrings().get("err_invalid_username_or_password");

		  Assert.assertEquals(actualErrTxt, expectedErrTxt);
	  }

	@Test
	public void successfulLogin() {
		loginPage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
		productsPage = loginPage.pressLoginBtn();

		String actualProductTitle = productsPage.getTitle();
		String expectedProductTitle = getStrings().get("product_title");

		Assert.assertEquals(actualProductTitle, expectedProductTitle);
	}
}

