package moolya.insteract.poc.pages;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import moolya.insteract.poc.utils.JavaUtils;

public class W_BasePage extends JavaUtils 
{
	String windowHandle = "";

	@FindBy(xpath="//span[text()='Manage User']/..")
	private WebElement manageUser_Btn;
	
	@FindBy(xpath="//button[@aria-label='Open user menu']")
	private WebElement openUserMenu_Btn;
	
	@FindBy(xpath="//md-icon[@md-font-icon='zmdi izmdi-my-travel']")
	private WebElement myTravel;
	
	@FindBy(xpath="//md-icon[@md-font-icon='zmdi izmdi-team-travel']")
	private WebElement myTeamTravel;
	
	
	@FindBy(xpath="//button[@ng-click='logout()']")
	private WebElement logout_Btn;
	
	public ManageUserPage clickOnManageUser() throws InterruptedException{
		clickElement(manageUser_Btn);
		return new ManageUserPage(driver);
	}
	
	public MyTravelPage clickOnMyTravel() throws InterruptedException{
		clickElement(myTravel);
		return new MyTravelPage(driver);
	}
	
	public MyTeamTravelPage clickOnMyTeamTravel() throws InterruptedException{
		clickElement(myTeamTravel);
		return new MyTeamTravelPage(driver);
	}

	public WebDriver driver;

	public W_BasePage(WebDriver driver)
	{
		this.driver= driver;
	}
	
	public void verifyUserMenuButton(){
		waitUntilElementclickable(openUserMenu_Btn);
		Assert.assertTrue(openUserMenu_Btn.isDisplayed(), "Login Not Success");
		Reporter.log("Login Success", true);
	}
	
	public void logout() throws InterruptedException{
		Thread.sleep(2000);
		clickElement(openUserMenu_Btn);
		clickElement(logout_Btn);
		Thread.sleep(5000);
	}
	
	public MailinatorPage goToMailinatorpage(){
		driver.get("https://www.mailinator.com/");
		return new MailinatorPage(driver);
	}

	public LoginPage openAppUrl(WebDriver driver) throws IOException{
		String appUrl = getPropValue("appUrl");
		driver.navigate().to(appUrl);
		return new LoginPage(driver);
	}
	
	public WebDriver launchWebApp() throws IOException
	{
		String appUrl = getPropValue("appUrl");
		String browser = getPropValue("browser");
		if (browser.equalsIgnoreCase("ff")) 
		{
			driver = new FirefoxDriver();
		}
		else if (browser.equalsIgnoreCase("chrome")) 
		{
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("chrome.switches","--disable-extensions");
			options.addArguments("chrome.switches","--disable-geolocation");
			driver = new ChromeDriver(options);
		}
		PageFactory.initElements(driver, this);
		driver.get(appUrl);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}
	
	public void mouseHoverOnElement(WebDriver driver,WebElement element){
		Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();
	}
	
	public void switchToNewTab(){
		for(String handle:driver.getWindowHandles())
			driver.switchTo().window(handle);
	}
	
	public void switchToMainTaB(){
		driver.close();
		driver.switchTo().window(windowHandle);
	}

	public void selectTextBox(WebElement element,String text) throws InterruptedException{
		sendKeysToElement(element, text);
		element.sendKeys(Keys.DOWN);
		element.sendKeys(Keys.ENTER);
	}

	public void closeBrowser(){

		driver.close();
		Reporter.log("*******Browser closed Successfully********", true);
	}


	public void waitUntilElementAppears(WebElement element){

		WebDriverWait wait = new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.visibilityOf(element));
	}


	public void waitUntilElementclickable(WebElement element){

		WebDriverWait wait = new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void selectDropdownValue(WebElement element,int index){
		Select dropdown = new Select(element);
		dropdown.selectByIndex(index);
	}

	public void selectDropdownValue(WebElement element,String value){
		Select dropdown = new Select(element);
		dropdown.selectByValue(value);
	}
	
	public void selectDropdownText(WebElement element, String text)
	{

		Select dropdown = new Select(element);
		dropdown.selectByVisibleText(text);
	}


	public  void scrollToElementViaJavascript(WebElement element) 
	{        
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);     
	}
	public  void scrollup(String xValue) 
	{    
		String parameter="scroll(" +xValue+ ",0)"; 
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript(parameter); //x value '500' can be altered
	}



	public void refreshPage()
	{
		driver.navigate().refresh();
		Reporter.log("Refresing Page", true);
	}

	public void clickElement(WebElement element) throws InterruptedException{
		try{
			waitUntilElementclickable(element);
			element.click();
		}catch(Exception e){
			Thread.sleep(5000);
			element.click();
		}
	}
	
	public void sendKeysToElement(WebElement element, String text) throws InterruptedException{
		try {
			waitUntilElementAppears(element);
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			Thread.sleep(5000);
			element.clear();
			element.sendKeys(text);
		}
	}
	

}


