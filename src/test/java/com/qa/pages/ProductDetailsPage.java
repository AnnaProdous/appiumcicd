package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import com.qa.MenuPage;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductDetailsPage extends MenuPage {
	TestUtils utils = new TestUtils();

	@AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
	private WebElement backToProductBtn;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[2]")
	private WebElement SLBTxt;

//	@AndroidFindBy (accessibility = "test-Price") private WebElement SLBPrice;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[1]")
	private WebElement SLBTitle;

	@AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
	@iOSXCUITFindBy(id = "test-BACK TO PRODUCTS")
	private WebElement backToProductsBtn;

	@iOSXCUITFindBy (id = "test-ADD TO CART") private WebElement addToCartBtn;


	public String getSLBTitle() {
		String title = getText(SLBTitle, "title is - ");
		return title;
	}

	public String getSLBTxt() {
		String txt = getText(SLBTxt, "txt is - ");
		return txt;
	}

	public String scrollToSLBPriceAndGetSLBPrice() {
		return getText(scrollToElement(), "");
	}

	public void scrollPage() {
		iOSScrollToElement();
	}

	public Boolean isAddToCartBtnDisplayed() {
		return addToCartBtn.isDisplayed();
	}

	public ProductsPage pressBackToProductsBtn() {
		click(backToProductsBtn, "navigate back to products page");
		return new ProductsPage();
	}
}