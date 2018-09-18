package com.letzautomate.managers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.letzautomate.constants.SeleniumConstants;

public class WebDriverManager {
	
	WebDriver driver;
	public WebDriver getLocalDriverInstance(String browserName) {		
		if (browserName.equals("CHROME")) {
			System.setProperty("webdriver.chrome.driver", SeleniumConstants.CHROME_DRIVER);
			driver = new ChromeDriver();		
		}	else if(browserName.equals("IE")){
			System.setProperty("webdriver.ie.driver", SeleniumConstants.IE_DRIVER);
			driver = new InternetExplorerDriver();
		} else if(browserName.equals("EDGE")) {
			System.setProperty("webdriver.edge.driver", SeleniumConstants.EDGE_DRIVER);
			driver = new EdgeDriver();
		}
		return driver;
	}
	public WebDriver getRemoteDriverInstance(String browserName, String remoteURL) throws MalformedURLException {		
		DesiredCapabilities cap = null;
		if (browserName.equals("CHROME")) {
			System.setProperty("webdriver.ie.driver", SeleniumConstants.CHROME_DRIVER);
			cap = getChromeCapabilities();
		}	else if(browserName.equals("IE")){
			
		} else if(browserName.equals("EDGE")) {
			
		}
		return new RemoteWebDriver(new URL(remoteURL), cap);
	}
	
	private DesiredCapabilities getChromeCapabilities(){
		DesiredCapabilities chromeCapabilities = new DesiredCapabilities();
		System.setProperty("webdriver.chrome.driver", SeleniumConstants.CHROME_DRIVER);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-infobars");
		options.addArguments("--test-type");
		options.addArguments("--start-maximized");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-default-apps");
		options.addArguments("--disable-plugins");
		
		chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
		chromeCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		chromeCapabilities.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
		chromeCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		return chromeCapabilities;
		
	}
	

}
