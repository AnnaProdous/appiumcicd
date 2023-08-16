package com.qa.reports;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport{
	static ExtentReports report;
    final static String filePath = "Spark.html";
    static Map<Integer, ExtentTest> extentTestMap = new HashMap();
    
		public synchronized static ExtentReports getReporter() {
			if(report == null) {
				ExtentSparkReporter spark = new ExtentSparkReporter("Spark.html");		
				spark.config().setDocumentTitle("Appium Framework");
				spark.config().setReportName("MyApp");
				spark.config().setTheme(Theme.DARK);
				report = new ExtentReports();
				report.attachReporter(spark);
			 } 
	        return report;
	    }
	
		public static synchronized ExtentTest getTest() {
	        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	    }
	 
	    public static synchronized ExtentTest startTest(String testName, String desc) {
	        ExtentTest test = getReporter().createTest(testName, desc);
	        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
	        return test;
	    }
	}