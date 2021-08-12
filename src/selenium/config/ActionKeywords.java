package selenium.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import selenium.driven.DriverScript;
import selenium.utils.Log;

public class ActionKeywords {
	public static WebDriver driver;
	public static Properties OR;

	public ActionKeywords() throws IOException {
		// TODO Auto-generated constructor stub
		FileInputStream fs = new FileInputStream(Constants.Path_OR);

		// Creating file system object for Object Repository text/property file
		OR = new Properties(System.getProperties());
		// Loading all the properties from Object Repository property file in to OR
		// object
		OR.load(fs);
	}

	public void openBrowser(String object, String data) {
		// This block of code will decide which browser type to start
		Log.info("Opening Browser");
		try {
			// If value of the parameter is Mozilla, this will execute
			if (data.equals("Mozilla")) {
				driver = new FirefoxDriver();
				Log.info("Mozilla browser started");
			} else if (data.equals("IE")) {
				// You may need to change the code here to start IE Driver
				driver = new InternetExplorerDriver();
				Log.info("IE browser started");
			} else if (data.equals("Chrome")) {
				driver = new ChromeDriver();
				Log.info("Chrome browser started");
			}

//			int implicitWaitTime = (10);
//			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);

		} catch (Exception e) {
			// This is to print the logs - Method Name & Error description/stack
			Log.info("Not able to open Browser --- " + e.getMessage());
			// Set the value of result variable to false
			DriverScript.bResult = false;
		}
	}

	public void navigate(String object, String data) {
		try {
			Log.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			// Constant Variable is used in place of URL
			driver.get(Constants.URL);
		} catch (Exception e) {
			Log.info("Not able to navigate --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public void click(String object, String data) {
		try {
			Log.info("Clicking on Webelement " + object);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	// Now this method accepts two value (Object name & Data)
	public void input(String object, String data) {
		try {
			Log.info("Entering the text in " + object);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		} catch (Exception e) {
			Log.error("Not able to enter to text field --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public void waitFor(String object, String data) throws Exception {
		try {
			Log.info("Wait for 2 seconds");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Log.error("Not able to Wait --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void closeBrowser(String object, String data) {
		try {
			Log.info("Closing the browser");
			driver.quit();
		} catch (Exception e) {
			Log.error("Not able to Close the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

}
