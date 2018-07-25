import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class ConfigForTests {
    protected WebDriver driver;
    private String baseURL = "https://auto.ria.com/";
    private static final Logger log = LogManager.getLogger(ConfigForTests.class);

    @Step("{0}")
    private static void logToAllure(String logger) {
        log.info(logger);
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] saveScreenshot(WebDriver driver) {
        logToAllure("Screenshot have been added to Allure Report.");
        return  ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @BeforeMethod
    public void openPage() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\drivers\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseURL);
        logToAllure(" Launch Auto Ria page");
    }

    @AfterMethod
    public void closePage(ITestResult result) {
        if(!result.isSuccess()){
            saveScreenshot(driver);
        }
        driver.quit();
        logToAllure("Close Auto Ria");
    }
}
