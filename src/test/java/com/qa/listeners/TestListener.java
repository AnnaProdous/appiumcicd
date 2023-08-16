package com.qa.listeners;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import com.qa.reports.ExtentReport;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.ITestContext;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;


public class TestListener implements ITestListener {
	TestUtils utils = new TestUtils();

	public void onTestFailure(ITestResult result) {
		if(result.getThrowable() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			result.getThrowable().printStackTrace(pw);
			utils.log().error(sw.toString());
		}
		
		BaseTest base = new BaseTest();
		File file = base.getDriver().getScreenshotAs(OutputType.FILE);
		
		byte[] encoded = null;
		try {
			encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Map <String, String> params = new HashMap <String, String>();
		params = result.getTestContext().getCurrentXmlTest().getAllParameters();
		
		String imagePath = "Screenshots" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" 
		+ params.get("deviceName") + File.separator + base.getDateTime() + File.separator + result.getTestClass().getRealClass().
		getSimpleName() + File.separator + result.getName() + ".png";
		
		String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;
		
		try {
		FileUtils.copyFile(file, new File(imagePath));
		Reporter.log("This is the sample screenshot");
		Reporter.log("<a href='" + completeImagePath + "'>img src='" + completeImagePath + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		ExtentReport.getTest().fail("Test Failed",
				MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
		ExtentReport.getTest().fail("Test Failed",
				MediaEntityBuilder.createScreenCaptureFromBase64String
				(new String(encoded, StandardCharsets.US_ASCII)).build());
		ExtentReport.getTest().fail(result.getThrowable());
		}

	@Override
	public void onTestStart(ITestResult result) {
		BaseTest base = new BaseTest();
		ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
				.assignCategory(base.getPlatformName() + base.getPlatformVersion() + "_" + base.getDeviceName())
				.assignAuthor("Ann");
	}
	 
	    @Override
	    public void onTestSuccess(ITestResult result) {
	        ExtentReport.getTest().log(Status.PASS, "Test Passed");
	}
	 
	    @Override
	    public void onTestSkipped(ITestResult result) {
	        ExtentReport.getTest().log(Status.SKIP, "Test Skipped");
	    }
	    
	    @Override
		public void onTestFailedButWithinSuccessPercentage(ITestResult result) {	
		}

		@Override
		public void onStart(ITestContext context) {
		}

		@Override
		public void onFinish(ITestContext context) {
			ExtentReport.getReporter().flush();		
		}
	}
