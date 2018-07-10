package com.ria.objects;


import com.ria.statements.AllureLogger;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Set;


public class MainPageHeadersLinks {

    private WebDriver driver;

    @FindBy (xpath = "//div[@id='header']//nav[@class='nav-head']/a[contains(text(),'RIA.com')]")
    private  WebElement riaComLink ;

    @FindBy (xpath = "//div[@id='header']//nav[@class='nav-head']/a[contains(text(),'RIA.com')]")
    private WebElement nedvizhemostLink ;

    private void driverWait(WebElement element) {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(element));

    }

    public MainPageHeadersLinks(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

       public String checkTheLoadPage(){

        String originalWindow = driver.getWindowHandle();
        final Set<String> oldWindowsSet = driver.getWindowHandles();

        riaComLink.click();
        AllureLogger.logToAllure("Click on Ria.Com link");

        String newWindow = (new WebDriverWait(driver, 10))
                .until(new ExpectedCondition<String>() {
                           public String apply(WebDriver driver) {
                               Set<String> newWindowsSet = driver.getWindowHandles();
                               newWindowsSet.removeAll(oldWindowsSet);
                               return newWindowsSet.size() > 0 ?
                                       newWindowsSet.iterator().next() : null;
                           }
                       }
                );

        driver.switchTo().window(newWindow);
        AllureLogger.logToAllure("Move to a new window");
        driverWait(driver.findElement(By.xpath("//header[contains(@class,'mhide')]")));
        String actualTitle = driver.getTitle();
        driver.close();
        AllureLogger.logToAllure("Close a new window");
        driver.switchTo().window(originalWindow);
        AllureLogger.logToAllure("Move to a parent window");


       return actualTitle;



    }



}

