package com.letzautomate.businessKeywords;

import org.openqa.selenium.WebDriver;

import com.letzautomate.pages.Login;

public class BusinessKeywords {
	WebDriver driver;
	Login login;
	public String status;
	public String actualResult;
	BusinessKeywords() {		
	}
	public BusinessKeywords(WebDriver driver) {
		this.driver = driver;
		this.login = new Login(driver);
	}
	
	public void launchApplication() {
		login.launchApplication();
		this.status = "PASS";
		this.actualResult = "launch application is passed";
	}
	
	public void addition(String params) {
		this.status = "PASS";
		this.actualResult = "addition is executed";
		System.out.println("addition is executed");
	}
	
	public void substraction(String params) {
		System.out.println("substraction is executed");
		this.status = "FAIL";
		this.actualResult = "substraction is executed";
	}
	
	public void multiplication(String params) {
		System.out.println("multiplication is executed");
		this.status = "PASS";
		this.actualResult = "multiplication is executed";
	}

}
