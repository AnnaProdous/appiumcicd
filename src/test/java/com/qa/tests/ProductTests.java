package com.qa.tests;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Driver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.MenuPage;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.testng.asserts.SoftAssert;

public class ProductTests extends  BaseTest{
	LoginPage loginPage;
	ProductsPage productsPage;
	SettingsPage settingsPage;
	ProductDetailsPage productDetailsPage;
	MenuPage menuPage;
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
	  ExtentTest test = ExtentReport.startTest("Test Setup: " + m.getName(), "Setting up test: " + m.getName());
	  utils.log().info("\n" + "****** starting test:" + m.getName() + "******" + "\n");

	  loginPage = new LoginPage();
	  productsPage = loginPage.login(
			  loginUsers.getJSONObject("validUser").getString("username"),
			  loginUsers.getJSONObject("validUser").getString("password")
	  );
	  menuPage = new MenuPage();
  }

  @AfterMethod
  public void afterMethod() {
//	  settingsPage = productsPage.pressSettingsBtn();
	  settingsPage = menuPage.pressSettingsBtn();
	  loginPage = settingsPage.pressLogoutBtn();
  }

	@Test
	public void validateProductOnProductsPage() {
		SoftAssert sa = new SoftAssert();

		String SLBTitle = productsPage.getSLBTitle();
		sa.assertEquals(SLBTitle, getStrings().get("products_page_slb_title"));

		String SLBPrice = productsPage.getSLBPrice();
		sa.assertEquals(SLBPrice, getStrings().get("products_page_slb_price"));

		sa.assertAll();
	}

	@Test
	public void validateProductOnProductDetailsPage() {
		ExtentTest test = ExtentReport.startTest("validateProductOnProductDetailsPage", "Test to validate product on product details page.");
		SoftAssert sa = new SoftAssert();

		productDetailsPage = productsPage.pressSLBTitle();

		String SLBTitle = productDetailsPage.getSLBTitle();
		sa.assertEquals(SLBTitle, getStrings().get("products_details_slb_title"));

		if(getPlatformName().equalsIgnoreCase("Android")) {
			String SLBPrice = productDetailsPage.scrollToSLBPriceAndGetSLBPrice();
			sa.assertEquals(SLBPrice, getStrings().get("product_details_page_slb_price"));
		}
		if(getPlatformName().equalsIgnoreCase("iOS")) {
			String SLBTxt = productDetailsPage.getSLBTxt();
			sa.assertEquals(SLBTxt, getStrings().get("products_details_slb_txt"));

			productDetailsPage.scrollPage();
			sa.assertTrue(productDetailsPage.isAddToCartBtnDisplayed());
		}
		sa.assertAll();
	}
}

