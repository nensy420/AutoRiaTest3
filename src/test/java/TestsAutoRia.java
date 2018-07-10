import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.ria.objects.*;
import com.ria.statements.AllureLogger;
import io.qameta.allure.Attachment;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestsAutoRia {
    private WebDriver driver;
    private String baseURL = "https://auto.ria.com/";

    @DataProvider
    public Object[][] validDataOfSearch() {
        return new Object[][]{
                {"Легковые", "Audi", "Q7", "Киев", "2014", "2017", "2000", "50000"}};
    }

    @DataProvider
    public Object[] invalidDataOfSearch() {
        return new Object[][]{
                {"Легковые", "Audi", "Q7", "Киев", "2014", "2017", "20000", "50"},
                {"Легковые", "BMW", "X5", "Киев", "2010", "2017", "20000", "10"}
        };
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        AllureLogger.logToAllure("Screenshot have been added to Allure Report.");
        return  ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }



    @BeforeMethod
    public void openPage() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\resourses\\drivers\\geckodriver.exe");
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


    @Test(dataProvider = "validDataOfSearch")
    public void checkDownloadPageTest(String transportName, String brandCarName, String modelName, String regionName, String yearFrom, String yearTo, String priceFrom, String priceTo)
    {
        MainPageSearchBu search = new MainPageSearchBu(driver);
        search.enterSearchParametersBu(transportName, brandCarName, modelName,  regionName,  yearFrom, yearTo, priceFrom, priceTo);
        Assert.assertTrue(search.checkIfTHePageDownload("Audi Q7","https://auto.ria.com/search/",driver.findElement(By.xpath("//div[@class='app-content']//following-sibling::div[@id='topFilter']/a"))),"ERROR:The page was not load");
        AllureLogger.logToAllure(" The results of search is loaded correctly");
        saveScreenshot(driver);


    }

    @Test(dataProvider = "validDataOfSearch")
    public void checkResultsOfSearchTestBu(String transportName, String brandCarName, String modelName, String regionName, String yearFrom,String yearTo,String priceFrom,String priceTo)
    {
        MainPageSearchBu search = new MainPageSearchBu(driver);
        search.enterSearchParametersBu(transportName, brandCarName, modelName,  regionName,  yearFrom, yearTo, priceFrom, priceTo);
        String expectedTitle = "Audi Q7";
        Assert.assertTrue(search.resultsOfSearch().contains(expectedTitle), " The results of search is not loaded");
        AllureLogger.logToAllure(" The results of search is loaded correctly");
        saveScreenshot(driver);

    }

    @Test(dataProvider = "invalidDataOfSearch")
    public void invalidDataSearchTest (String transportName, String brandCarName, String modelName, String regionName, String yearFrom,String yearTo,String priceFrom,String priceTo)
    {
        MainPageSearchBu search = new MainPageSearchBu(driver);
        search.enterSearchParametersBu(transportName, brandCarName, modelName,  regionName,  yearFrom, yearTo, priceFrom, priceTo);
        String expectedErrorMessage ="Объявлений не найдено";
        String actualErrorMessage = search.errorMessage();
        System.out.println(actualErrorMessage);
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage)," The error message wasn't load");
        saveScreenshot(driver);
        AllureLogger.logToAllure(" The error message is displayed");

    }


    @Test()
    public void registrationWithInvalidDataTest() {
        AuthorizationCheck check = new AuthorizationCheck(driver);
        String expectedErrorMessage = "неверный  мобильный номер телефона";
        check.registration("236lo659");
        String actualErrorMessage = check.getErrorMessageRegistration();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), " The registration with invalid data was successful");
        AllureLogger.logToAllure(" The registration was failed");
        saveScreenshot(driver);
    }

    @Test()
    public void checkResultsOfSearchTestNew() {
        MainPageSearchNewCars search = new MainPageSearchNewCars(driver);
        search.enterSearchParametersNew("Легковые", "BMW", "X5", "Киев", "2010", "2017", "2000", "100000");
        String expectedResult = "newauto";
        Assert.assertTrue(search.resultsOfSearch().contains(expectedResult), " The search page was not load");
        AllureLogger.logToAllure(" The results of search is loaded correctly");
        saveScreenshot(driver);
    }

    @Test()
    public void checkHeadersLink(){
        MainPageHeadersLinks link = new MainPageHeadersLinks(driver);
        String expectedTitle = "RIA.com ™ — доска бесплатных частных объявлений Украины";
        Assert.assertTrue(link.checkTheLoadPage().contains(expectedTitle), " The Ria.come page not loaded");
        AllureLogger.logToAllure(" The Ria.come page was successfully load after clicking on link");
        saveScreenshot(driver);
    }


    @Test()
    public void apiTest() throws IOException {
        Api apiObject = new Api(driver);
        apiObject.getRequest();
    }
    @Test()
    public void apiTestRestAssured()  {
        Api apiObject = new Api(driver);
        apiObject.getRequestWithRestAssure();
    }

    @Test() void apiTestRestAssured2(){

        given().get("https://developers.ria.com/auto/new/models?marka_id=9&category_id=1&api_key=Q7qNVxdwbZV6xGmpD37IjPjy0AuCRmkYK5lNnRpd").
                then().
                statusCode(200).
                body("marka_id[0]",equalTo(9));
    }

 }

