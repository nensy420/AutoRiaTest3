package com.ria.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;

public class DriverManager {
    private static Driver driverType;
    private static WebDriver driver = null;

    private DriverManager(){
    }
    public enum Driver{
        Firefox, IE, Chrome
    }

    public static void setDriver(Driver driverType){
        DriverManager.driverType = driverType;
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            switch (driverType) {

                case Firefox:
                    System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\drivers\\geckodriver.exe");
                    driver = new FirefoxDriver();
                    break;
                case IE:
                    driver = new InternetExplorerDriver();
                    break;
                case Chrome:
                    System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\drivers\\chromedriver.exe");
                    driver = new ChromeDriver();
                    break;
                default:
                    throw new WebDriverException("Driver is not defined.");
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return driver;
    }

    public static void quitDriver(){
        if(driver != null){
            driver.quit();
            driver = null;
        }
    }
}
