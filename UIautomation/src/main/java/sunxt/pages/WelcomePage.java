package sunxt.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WelcomePage extends BasePage {

	public WelcomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(linkText = "Join Free for a month") 
	private WebElement sign_up;
	
	@FindBy(linkText = "Sign in") 
	private WebElement sign_in;
	
	public SignUpPage Click_SignUp(){
	sign_up.click();			
	return new SignUpPage(driver);
	}
	
	public SignInPage Click_SignIn(){
	sign_in.click();			
	return new SignInPage(driver);
	}
	

}
