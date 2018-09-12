package com.letzautomate.driver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.letzautomate.businessKeywords.BusinessKeywords;
import com.letzautomate.constants.FrameworkConstants;
import com.letzautomate.managers.WebDriverManager;
import com.letzautomate.utilities.Excel;

public class Driver {
	WebDriver driver;
	Method[] methods;
	Method method;
	String keyword;
	String inputOrExp;
	String status;
	String actualResult;	
	
	@Parameters({"browserName", "testcases", "remoteHostURL"})
	@Test
	public void runSuite(String browserName, String testcases, String remoteHostURL) throws SecurityException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {
		WebDriverManager webDriverManager = new WebDriverManager();
		this.driver = webDriverManager.getDriverInstance(browserName);
		BusinessKeywords businessKeywords = new BusinessKeywords(driver);	
		Excel excel = new Excel();
		String[] tcsToExecuteArray = excel.getTCToExecute(FrameworkConstants.TESTCASES_DIRECTORY + "/" + testcases +".xls");
		int numOfTestcases = tcsToExecuteArray.length;
		for(int tcIndex=0; tcIndex < numOfTestcases; tcIndex ++) {
			System.out.println(tcsToExecuteArray[tcIndex]);
		}
		
		String[][] testSuite = excel.getTestSuite(FrameworkConstants.TESTCASES_DIRECTORY + "/" + testcases +".xls");
		int totalSteps = testSuite.length;
		System.out.println(totalSteps);
		
		for(int testStepIndex = 1; testStepIndex < totalSteps - 1 ; testStepIndex ++) {
			keyword = testSuite[testStepIndex ][FrameworkConstants.KEYWORD_COLUMN - 1].trim();
			inputOrExp = testSuite[testStepIndex][FrameworkConstants.INPUT_COLUMN - 1];
			if (inputOrExp.length() > 1) {
				inputOrExp = testSuite[testStepIndex][FrameworkConstants.INPUT_COLUMN - 1].trim();
			}						
			if (inputOrExp.equals(" ")) {				
				method = businessKeywords.getClass().getDeclaredMethod(keyword);
				method.invoke(businessKeywords);				
				System.out.println(keyword + " is executed");
			} else {
				method =  businessKeywords.getClass().getDeclaredMethod(keyword , String.class);
				method.invoke(businessKeywords, inputOrExp);		
				System.out.println(keyword + " is executed");
			}
			testSuite[testStepIndex ][FrameworkConstants.STATUS_COLUMN - 1] = businessKeywords.status;
			testSuite[testStepIndex ][FrameworkConstants.ACTUALRESULT_COLUMN - 1] = businessKeywords.actualResult;
			if (businessKeywords.status.equals("FAIL")) {
				/*TakesScreenshot screenShot = (TakesScreenshot) driver;
				File file = screenShot.getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(file, new File(FrameworkConstants.SCREENSHOTS_DIRECTORY + "/" + keyword + ".png") );
				testSuite[testStepIndex ][FrameworkConstants.SCREENSHOT_COLUMN - 1] = FrameworkConstants.SCREENSHOTS_DIRECTORY + "/" + keyword + ".png";*/
			}
		}		
		excel.writeDataArrayToExcel(FrameworkConstants.RESULTS_DIRECTORY + "/results.xls", testSuite);
	}
	
	@AfterClass
	public void quitDriver() {
		driver.close();
	}
	

}
