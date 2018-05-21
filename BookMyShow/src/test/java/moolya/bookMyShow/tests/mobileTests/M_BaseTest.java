package moolya.bookMyShow.tests.mobileTests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import moolya.bookMyShow.pages.mobilePages.M_BasePage;
import moolya.bookMyShow.utils.AppiumServerUtils;

@SuppressWarnings("unused")
public class M_BaseTest {
	
	public AppiumDriver<MobileElement> mdriver;
	
	M_BasePage basepage;
	
	@BeforeSuite
	public void setUp() throws IOException, InterruptedException{
		AppiumServerUtils.startAppium();
		basepage = new M_BasePage(mdriver);
		mdriver = basepage.launchmobApp();
	}
	
	@AfterSuite
	public void tearDown() throws IOException, InterruptedException
	{
		AppiumServerUtils.stopAppium();
		mdriver.closeApp();
	}
	
	@AfterMethod
	public void catchExceptions(ITestResult result) throws IOException, InterruptedException 
	{    
		String dir = System.getProperty("user.dir");
		String[] clsParts = result.getInstanceName().split("\\.");
		String clsName = clsParts[(clsParts.length)-1];
		if(!result.isSuccess()){            
			File scrFile = ((TakesScreenshot)mdriver).getScreenshotAs(OutputType.FILE);
			String file =dir+"/screenshots/"+clsName+".png";
			try {
				FileUtils.copyFile(scrFile, new File(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
