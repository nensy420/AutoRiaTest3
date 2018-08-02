import com.ria.browsers.DriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

import static com.ria.browsers.DriverManager.getDriver;
import static com.ria.browsers.DriverManager.quitDriver;
import static com.ria.browsers.DriverManager.setDriver;

public class ConfigForTests {
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
        setDriver(DriverManager.Driver.Firefox);
 //       setDriver(DriverManager.Driver.Chrome);
        getDriver().get(baseURL);
        logToAllure(" Launch Auto Ria page");
    }

    @AfterMethod
    public void closePage(ITestResult result) {
        if(!result.isSuccess()){
            saveScreenshot(getDriver());
        }
       quitDriver();
        logToAllure("Close Auto Ria");
    }
}
