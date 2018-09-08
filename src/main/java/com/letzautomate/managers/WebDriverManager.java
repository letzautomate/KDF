package com.letzautomate.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.letzautomate.constants.SeleniumConstants;

public class WebDriverManager {
	
	WebDriver driver;
	public WebDriver getDriverInstance(String browserName) {		
		if (browserName.equals("CHROME")) {
			System.setProperty("webdriver.chrome.driver", SeleniumConstants.CHROMEDRIVERPATH);
			driver = new ChromeDriver();
			return driver;
		}	
		return driver;
	}

}
