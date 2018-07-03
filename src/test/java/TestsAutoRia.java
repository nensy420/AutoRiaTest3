import com.autoRia.pageObjects.AuthorizationCheck;
import com.autoRia.pageObjects.MainPageSearchBu;
import com.autoRia.pageObjects.MainPageSearchNewCars;
import com.autoRia.statements.AllureLogger;
import io.qameta.allure.Attachment;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.util.concurrent.TimeUnit;

public class TestsAutoRia {
    private WebDriver driver;
    private String baseURL = "https://auto.ria.com/";


    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        AllureLogger.logToAllure("Screenshot have been added to Allure Report.");
        return  ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }



    @BeforeMethod
    public void openPage() {
        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseURL);
        AllureLogger.logToAllure(" Launch Auto Ria page");

    }

    @AfterMethod
    public void closePage() {
        driver.quit();
        AllureLogger.logToAllure("Close Auto Ria");

    }


    @Test()
    public void checkDownloadPageTest(){
        MainPageSearchBu search = new MainPageSearchBu(driver);
        search.enterSearchParametersBu("Легковые", "Audi", "Q7", "Киев", "2014", "2017", "2000", "50000");
        String actualTitle = driver.getTitle();
        String expectedTitle = "AUTO.RIA - Базар авто №1: автосалоны, продажа авто б.у. и новых. Автопоиск по";
        Assert.assertTrue(actualTitle.contains(expectedTitle),"\"ERROR: The search page was not load");
        AllureLogger.logToAllure(" The search page was successfully load");
        saveScreenshot(driver);


    }

    @Test()
    public void checkResultsOfSearchTestBu(){
        MainPageSearchBu search = new MainPageSearchBu(driver);
        search.enterSearchParametersBu("Легковые", "Audi", "Q7", "Киев", "2014", "2017", "2000", "50000");
        String expectedTitle = "Audi Q7";
        Assert.assertTrue(search.resultsOfSearch().contains(expectedTitle),"ERROR: " + expectedTitle + " was not found on the Page");
        AllureLogger.logToAllure(" The results of search is loaded correctly");
        saveScreenshot(driver);

    }

    @Test()
    public void invalidDataSearchTest(){
        MainPageSearchBu search = new MainPageSearchBu(driver);
        search.enterSearchParametersBu("Легковые", "Audi", "Q7", "Киев", "2014", "2017", "5000", "2");
        String expectedErrorMessage ="Объявлений не найдено";
        String actualErrorMessage = search.errorMessage();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),"ERROR: The error message wasn't load");
        saveScreenshot(driver);
        AllureLogger.logToAllure(" The error message is displayed");

    }


    @Test()
    public void registrationWithInvalidDataTest() {
        AuthorizationCheck check = new AuthorizationCheck(driver);
        String expectedErrorMessage = "неверный  мобильный номер телефона";
        check.registration("236lo659");
        String actualErrorMessage = check.getErrorMessageRegistration();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), "\"ERROR: The registration with invalid data was successful");
        AllureLogger.logToAllure(" The registration was failed");
        saveScreenshot(driver);
    }

    @Test()
    public void checkResultsOfSearchTestNew() {
        MainPageSearchNewCars search = new MainPageSearchNewCars(driver);
        search.enterSearchParametersNew("Легковые", "BMW", "X5", "Киев", "2010", "2017", "2000", "100000");
        String expectedResult = "newauto";
        Assert.assertTrue(search.resultsOfSearch().contains(expectedResult), "\"ERROR: The search page was not load");
        AllureLogger.logToAllure(" The results of search is loaded correctly");
        saveScreenshot(driver);
    }

//    @Test()
//    public void checkHeadersLink(){
//        MainPageHeadersLinks link = new MainPageHeadersLinks(driver);
//        link.checkTheLoadPage();
//    }

 }

