package com.ria.objects;



import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AuthorizationCheck {
    private WebDriver driver;

    @FindBy (xpath = "//div[@id='header']//div[@class='container']//span[@class='tl']")
    private WebElement enterTo;

    @FindBy(xpath = "//form[@id='login-form']/following-sibling::div[@class='login-link']/a")
    private WebElement registerLink;

    @FindBy(xpath = "//div[@class='login-rows']//following-sibling::input")
    private WebElement telephoneField;

    @FindBy(xpath = "//div[@class='sub-button']//following-sibling::input")
    private WebElement continueButton;

    @FindBy(id = "emailloginform-email")
    private WebElement emailField;

    @FindBy(id = "emailloginform-password")
    private WebElement passwardField;

    @FindBy(xpath = "//form[@id='login-form']/child::div[@class='login-link']//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//form[@id='login-form']/child::div[@class='login-rows']//p[@class='error']")
    private WebElement fieldAttantion;

    private static final Logger log = LogManager.getLogger(AuthorizationCheck.class);

    private WebElement waitTime(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    public void clickOnEnterAndSwitchToFrame(){
        waitTime(enterTo).click();
        logToAllure("Click on enter to");
        driver.switchTo().frame("login_frame");
        logToAllure("Switch to frame");
    }

    public void clickOnRegister(){
         waitTime(registerLink).click();
         logToAllure("Click on registration link");
    }

    public void inputTelephone(String number){
       waitTime(telephoneField).sendKeys(number);
       logToAllure("Input the telephone number");
    }

    private void clickOnContinueButton(){
        waitTime(continueButton).click();
        logToAllure(" Click on the continue button");
    }

    @Step("{0}")
    private static void logToAllure(String logger) {
        log.info(logger);
    }

    public AuthorizationCheck(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void registration(String number){
        clickOnEnterAndSwitchToFrame();
        clickOnRegister();
        inputTelephone(number);
        clickOnContinueButton();
    }

    public String getErrorMessageRegistration(){
        String attantionMessage = fieldAttantion.getAttribute("innerHTML");
        logToAllure(" Get the message");
        return attantionMessage;
    }

}
