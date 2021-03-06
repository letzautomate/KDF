package com.letzautomate.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
	WebDriver driver;
	public Login(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//@CacheLookup
	@FindBy(xpath="//input[@name='userName']")
	public WebElement userName;
	

	
	public void enterUsername() {
		userName.sendKeys("abcd");
	}

}
