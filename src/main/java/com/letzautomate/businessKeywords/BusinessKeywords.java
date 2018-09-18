package com.letzautomate.businessKeywords;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.letzautomate.pages.Login;
import com.letzautomate.utilities.ReadProperties;

public class BusinessKeywords {
	WebDriver driver;
	Login login;
	public Properties properties;
	public String status;
	public String actualResult;
	BusinessKeywords() {		
	}
	public BusinessKeywords(WebDriver driver) throws IOException {
		this.driver = driver;
		this.login = new Login(driver);
		this.properties = ReadProperties.getProperties();
	}
	
	public void tcStart() {
		this.status = "PASS";
		this.actualResult = "Execution started";
	}
	
	public void tcEnd() {
		this.status = "PASS";
		this.actualResult = "Execution Ended";
	}
	
	public void launchApplication() {
		try{
		driver.get(properties.getProperty("url"));
		this.status = "PASS";
		this.actualResult = "launch application is passed";
		}catch (Exception e) {
			this.status = "FAIL";
			this.actualResult = "There was an error and the error is " + e.toString();
		}
		
	}
	public void enterUsername() {
		login.enterUsername();
		this.status = "PASS";
		this.actualResult = "userName is entered";
	}
	
	public void newAddition() {
		System.out.println("This is new Addition");
	}
	
	public void refresh() {
		driver.get("http://newtours.demoaut.com");
		this.status = "PASS";
		this.actualResult = "refresh is successful";
	}
	

	public void addition(String params) {
		this.status = "PASS";
		this.actualResult = "addition is executed";
		System.out.println("added");
	}
	
	public void substraction(String params) {
		this.status = "FAIL";
		this.actualResult = "substraction is executed";
	}
	
	public void multiplication(String params) {
		this.status = "PASS";
		this.actualResult = "multiplication is executed";
	}

}
