package com.autoRia.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MainPageHeadersLinks {

    private WebDriver driver;

    @FindBy (xpath = "//div[@id='header']//nav[@class='nav-head']/a[contains(text(),'RIA.com')]")
    private  WebElement riaComLink ;

    @FindBy (xpath = "//div[@id='header']//nav[@class='nav-head']/a[contains(text(),'RIA.com')]")
    private WebElement nedvizhemostLink ;
    private static final Logger Log = Logger.getLogger(MainPageSearchBu.class);

    public MainPageHeadersLinks(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

//    private void waitTime() {
//        WebDriverWait wait = new WebDriverWait(driver, 20);
//        wait.until(ExpectedConditions.visibilityOf());
//        Log.info("[INFO] Wait until element to be clickable");
//    }

    public void checkTheLoadPage(){


        String originalWindow = driver.getWindowHandle();
        final Set<String> oldWindowsSet = driver.getWindowHandles();

        riaComLink.click();

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

        System.out.println("New window title: " + driver.getTitle());
        driver.close();

        driver.switchTo().window(originalWindow);
        System.out.println("Old window title: " + driver.getTitle());






//        riaComLink.click();
//        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
//        String expectedTitle ="RIA.com";
//        String actualTitle=driver.getTitle();
//        System.out.println(actualTitle);
//        Assert.assertTrue(actualTitle.contains(expectedTitle));
//        driver.navigate().to("https://auto.ria.com/");

    }






}

