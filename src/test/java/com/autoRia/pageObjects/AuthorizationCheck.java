package com.autoRia.pageObjects;

import com.autoRia.statements.AllureLogger;
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





    public AuthorizationCheck(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

    }


    private WebElement waitTime(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }


    private void clickOnEnterAndSwitchToFrame(){
        waitTime(enterTo).click();
        AllureLogger.logToAllure("Click on enter to");
        driver.switchTo().frame("login_frame");
        AllureLogger.logToAllure("Switch to frame");
    }

    private void clickOnRegister(){
         waitTime(registerLink).click();
        AllureLogger.logToAllure("Click on registration link");

    }

    private void inputTelephone(String number){
       waitTime(telephoneField).sendKeys(number);
        AllureLogger.logToAllure("Input the telephone number");
    }

    private void clickOnContinueButton(){
        waitTime(continueButton).click();
        AllureLogger.logToAllure(" Click on the continue button");
    }

    public void registration(String number){
        clickOnEnterAndSwitchToFrame();
        clickOnRegister();
        inputTelephone(number);
        clickOnContinueButton();
    }

    public String getErrorMessageRegistration(){
        String attantionMessage = fieldAttantion.getAttribute("innerHTML");
        AllureLogger.logToAllure(" Get the message");
        return attantionMessage;

    }

    public void enterTheTelephoneAndPassward(String telephone,String password){
        clickOnContinueButton();
        telephoneField.sendKeys(telephone);
        passwardField.sendKeys(password);
        submitButton.click();
    }



}
