package com.qa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.aventstack.extentreports.Status;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.aventstack.extentreports.model.Test;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import static java.time.Duration.*;


public class BaseTest extends ExtentReport{
	protected static ThreadLocal <AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
	protected static ThreadLocal <Properties> props = new ThreadLocal<Properties>();
	protected static ThreadLocal <HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
	protected static ThreadLocal<JavascriptExecutor> jsExecutorThreadLocal = new ThreadLocal<JavascriptExecutor>();
	protected static ThreadLocal <String> platformName = new ThreadLocal<String>();
	protected static ThreadLocal <String> platformVersion = new ThreadLocal<String>();
	protected static ThreadLocal <String> dateTime = new ThreadLocal<String>();
	protected static ThreadLocal <String> deviceName = new ThreadLocal<String>();
	private static AppiumDriverLocalService server;
	TestUtils utils = new TestUtils();

	public AppiumDriver getDriver() {
		return driver.get();
	}

	public void setDriver(AppiumDriver driver2) {
		driver.set(driver2);
	}

	public Properties getProps() {
		return props.get();
	}

	public void setProps(Properties props2) {
		props.set(props2);
	}

	public HashMap<String, String> getStrings() {
		return strings.get();
	}

	public void setStrings(HashMap<String, String> strings2) {
		strings.set(strings2);
	}

	public String getPlatformName() {
		return platformName.get();
	}

	public void setPlatformName(String platform2) {
		platformName.set(platform2);
	}

	public String getPlatformVersion() {
		return platformVersion.get();
	}

	public void setPlatformVersion(String platform2) {
		platformVersion.set(platform2);
	}

	public String getDateTime() {
		return dateTime.get();
	}

	public void setDateTime(String dateTime2) {
		dateTime.set(dateTime2);
	}

	public String getDeviceName() {
		return deviceName.get();
	}

	public void setDeviceName(String deviceName2) {
		deviceName.set(deviceName2);
	}

	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}

	@BeforeMethod
	public void beforeMethod() {
		((CanRecordScreen) getDriver()).startRecordingScreen();
	}

	@BeforeSuite
	public void beforeSuite() throws Exception, Exception {
		ThreadContext.put("ROUTINGKEY", "ServerLogs");
		server = getAppiumService();
		if(!checkIfAppiumServerIsRunnning(4723)) {
			server.start();
			server.clearOutPutStreams();
			utils.log().info("Appium server started");
		} else {
			utils.log().info("Appium server already is running");
		}
	}

	public boolean checkIfAppiumServerIsRunnning(int port) throws Exception {
		boolean isAppiumServerRunning = false;
		ServerSocket socket;
		try {
			socket = new ServerSocket(port);
			socket.close();
		} catch (IOException e) {
			System.out.println("1");
			isAppiumServerRunning = true;
		} finally {
			socket = null;
		}
		return isAppiumServerRunning;
	}

	@AfterSuite (alwaysRun = true)
	public void afterSuite() {
		if(server.isRunning()){
			server.stop();
			utils.log().info("Appium server stopped");
		}
	}
	
	public AppiumDriverLocalService getAppiumServerDefault() {
		return AppiumDriverLocalService.buildDefaultService();
	}
	
	public AppiumDriverLocalService getAppiumService() {
		HashMap<String, String> environment = new HashMap<String, String>();
		environment.put("PATH", "/Users/annprodous/.gem/ruby/3.1.3/bin:/Users/annprodous/.rubies/ruby-3.1.3/lib/ruby/gems/3.1.0/bin:/Users/annprodous/.rubies/ruby-3.1.3/bin:/Users/annprodous/Library/Android/sdk/platform-tools:/Users/annprodous/Library/Android/sdk/cmdline-tools:/Library/Java/JavaVirtualMachines/jdk-15.0.2.jdk/Contents/Home/bin:/usr/local/opt/ruby/bin:/usr/local/lib/ruby/gems/2.7.0/bin:/opt/homebrew/bin:/opt/homebrew/sbin:/opt/homebrew/bin:/opt/homebrew/sbin:/opt/homebrew/bin:/opt/homebrew/sbin:/opt/homebrew/bin:/opt/homebrew/sbin:/opt/homebrew/bin:/opt/homebrew/sbin:/usr/local/bin:/System/Cryptexes/App/usr/bin:/usr/bin:/bin:/usr/sbin:/sbin:~/.dotnet/tools:/Library/Apple/usr/bin:/var/run/com.apple.security.cryptexd/codex.system/bootstrap/usr/local/bin:/var/run/com.apple.security.cryptexd/codex.system/bootstrap/usr/bin:/var/run/com.apple.security.cryptexd/codex.system/bootstrap/usr/appleinternal/bin:/Users/annprodous/.rvm/bin/" + System.getenv("PATH"));
		environment.put("ANDROID_HOME", "/Users/annprodous/Library/Android/sdk");
		return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
				.usingDriverExecutable(new File("/usr/local/bin/node"))
				.withAppiumJS(new File("/opt/homebrew/lib/node_modules/appium/build/lib/main.js"))
				.usingPort(4723).withArgument(GeneralServerFlag.SESSION_OVERRIDE)
				.withEnvironment(environment)
				.withLogFile(new File("ServerLogs/server.log")));
	}

	@Parameters({"emulator", "platformName", "platformVersion", "udid", "deviceName", "systemPort",
			"chromeDriverPort", "wdaLocalPort", "webkitDebugProxyPort"})

	@BeforeTest
	public void beforeTest(@Optional("androidOnly")String emulator, String platformName, String platformVersion, String udid, String deviceName,
						   @Optional("androidOnly")String systemPort, @Optional("androidOnly")String chromeDriverPort,
						   @Optional("iOSOnly")String wdaLocalPort, @Optional("iOSOnly")String webkitDebugProxyPort) throws Exception {
		setDateTime(utils.dateTime());
		setPlatformName(platformName);
		setPlatformVersion(platformVersion);
		setDeviceName(deviceName);
		URL url;
		InputStream inputStream = null;
		InputStream stringsis = null;
		Properties props = new Properties();
		AppiumDriver driver;

		String strFile = "logs" + File.separator + platformName + "_" + deviceName;
		File logFile = new File(strFile);
		if (!logFile.exists()) {
			logFile.mkdirs();
		}
		//route logs to separate file for each thread
		ThreadContext.put("ROUTINGKEY", strFile);
		utils.log().info("log path: " + strFile);

		try {
			props = new Properties();
			String propFileName = "config.properties";
			String xmlFileName = "strings/strings.xml";

			utils.log().info("load " + propFileName);
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			props.load(inputStream);
			setProps(props);

			utils.log().info("load " + xmlFileName);
			stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
			setStrings(utils.parseStringXML(stringsis));

			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setCapability("platformName", platformName);
			desiredCapabilities.setCapability("deviceName", deviceName);
			desiredCapabilities.setCapability("udid", udid);
			url = new URL(props.getProperty("appiumURL"));

			switch (platformName) {
				case "Android":
					desiredCapabilities.setCapability("automationName", props.getProperty("androidAutomationName"));
					desiredCapabilities.setCapability("appPackage", props.getProperty("androidAppPackage"));
					desiredCapabilities.setCapability("appActivity", props.getProperty("androidAppActivity"));
		      if(emulator.equalsIgnoreCase("true")) {
				  desiredCapabilities.setCapability("avd", deviceName);
				  desiredCapabilities.setCapability("avdLaunchTimeout", 120000);
			  }
					desiredCapabilities.setCapability("systemPort", systemPort);
					desiredCapabilities.setCapability("chromeDriverPort", chromeDriverPort);
					String androidAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
							+ File.separator + "resources" + File.separator + "app" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
					utils.log().info("appUrl is" + androidAppUrl);
					desiredCapabilities.setCapability("app", androidAppUrl);
					driver = new AndroidDriver(url, desiredCapabilities);
					break;

				case "iOS":
					desiredCapabilities.setCapability("automationName", props.getProperty("iOSAutomationName"));
					String iOSAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
							+ File.separator + "resources" + File.separator + "app" + File.separator + "iOS.Simulator.SauceLabs.Mobile.Sample.app.2.7.1.app";
					utils.log().info("appUrl is" + iOSAppUrl);
					desiredCapabilities.setCapability("bundleId", props.getProperty("iOSBundleId"));
					desiredCapabilities.setCapability("wdaLocalPort", wdaLocalPort);
					desiredCapabilities.setCapability("webkitDebugProxyPort", webkitDebugProxyPort);
					desiredCapabilities.setCapability("app", iOSAppUrl);
					driver = new IOSDriver(url, desiredCapabilities);
					break;
				default:
					throw new Exception("Invalid platform! - " + platformName);
			}
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutorThreadLocal.set(jsExecutor);
			setDriver(driver);
			utils.log().info("driver initialized: " + driver);
		} catch (Exception e) {
			utils.log().fatal("driver initialization failure. ABORT!!!\n" + e.toString());
			throw e;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (stringsis != null) {
				stringsis.close();
			}
		}
	}

	public void waitForVisibility(WebElement e) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(TestUtils.WAIT));
		wait.until(ExpectedConditions.visibilityOf(e));
	}
  
  public void clear(WebElement e) {
	  waitForVisibility(e);
	  e.clear();
  }
  
  public void click(WebElement e) {
	  waitForVisibility(e);
	  e.click();
  }
	public void click(WebElement e, String msg) {
		waitForVisibility(e);
		utils.log().info(msg);
		ExtentReport.getTest().log(Status.INFO, msg);
		e.click();
	}
  
  public void sendKeys(WebElement e, String txt) {
	  waitForVisibility(e);
	  e.sendKeys(txt);
  }
	public void sendKeys(WebElement e, String txt, String msg) {
		waitForVisibility(e);
		utils.log().info(msg);
		ExtentReport.getTest().log(Status.INFO, msg);
		e.sendKeys(txt);
	}

	public String getAttribute(WebElement e, String attribute) {
		waitForVisibility(e);
		return e.getAttribute(attribute);
	}

	public JavascriptExecutor getJsExecutor() {
		return jsExecutorThreadLocal.get();
	}

	public String getText(WebElement e, String msg) {
		String txt = null;
		switch(getPlatformName()) {
			case "Android":
				txt = getAttribute(e, "text");
				break;
			case "iOS":
				txt = getAttribute(e, "label");
				break;
		}
		utils.log().info(msg + txt);
		ExtentReport.getTest().log(Status.INFO, msg + txt);
		return txt;
	}

	public void closeApp() {
		switch(getPlatformName()){
			case "Android":
				((InteractsWithApps) getDriver()).terminateApp(getProps().getProperty("androidAppPackage"));
				break;
			case "iOS":
				((InteractsWithApps) getDriver()).terminateApp(getProps().getProperty("iOSBundleId"));
		}
	}

	public void launchApp() {
		switch(getPlatformName()){
			case "Android":
				((InteractsWithApps) getDriver()).activateApp(getProps().getProperty("androidAppPackage"));
				break;
			case "iOS":
				((InteractsWithApps) getDriver()).activateApp(getProps().getProperty("iOSBundleId"));
		}
	}

	public WebElement scrollToElement() {
		return getDriver().findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
						+ "new UiSelector().description(\"test-Price\"));"));
	}

	public void iOSScrollToElement() {
//	  RemoteWebElement element = (RemoteWebElement)getDriver().findElement(By.name("test-ADD TO CART"));
//	  String elementID = element.getId();
		HashMap<String, String> scrollObject = new HashMap<String, String>();
//	  scrollObject.put("element", elementID);
		scrollObject.put("direction", "down");
//	  scrollObject.put("predicateString", "label == 'ADD TO CART'");
//	  scrollObject.put("name", "test-ADD TO CART");
//	  scrollObject.put("toVisible", "sdfnjksdnfkld");
		getDriver().executeScript("mobile:scroll", scrollObject);
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
		if(getDriver() != null){
			getDriver().quit();
		}
	}
}

