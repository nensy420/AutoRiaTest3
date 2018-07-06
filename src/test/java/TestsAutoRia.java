import com.ria.objects.AuthorizationCheck;
import com.ria.objects.MainPageHeadersLinks;
import com.ria.objects.MainPageSearchBu;
import com.ria.objects.MainPageSearchNewCars;
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


import java.util.concurrent.TimeUnit;

public class TestsAutoRia {
    private WebDriver driver;
    private String baseURL = "https://auto.ria.com/";

    @DataProvider
    public Object[] validDataOfSearch() {
        return new Object[][]{
                {"Легковые", "Audi", "Q7", "Киев", "2014", "2017", "2000", "50000"}
    };
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
    public void checkDownloadPageTest(String transportName, String brandCarName, String modelName, String regionName, String yearFrom,String yearTo,String priceFrom,String priceTo)
    {
        MainPageSearchBu search = new MainPageSearchBu(driver);
        search.enterSearchParametersBu(transportName, brandCarName, modelName,  regionName,  yearFrom, yearTo, priceFrom, priceTo);
        String actualTitle = driver.getTitle();
        String expectedTitle = "AUTO.RIA - Базар авто №1: автосалоны, продажа авто б.у. и новых. Автопоиск по";
        Assert.assertTrue(actualTitle.contains(expectedTitle),"\"ERROR: The search page was not load");
        AllureLogger.logToAllure(" The search page was successfully load");
        saveScreenshot(driver);


    }

    @Test(dataProvider = "validDataOfSearch")
    public void checkResultsOfSearchTestBu(String transportName, String brandCarName, String modelName, String regionName, String yearFrom,String yearTo,String priceFrom,String priceTo)
    {
        MainPageSearchBu search = new MainPageSearchBu(driver);
        search.enterSearchParametersBu(transportName, brandCarName, modelName,  regionName,  yearFrom, yearTo, priceFrom, priceTo);
        Assert.assertTrue(search.checkIfTHePageDownload("Audi Q7","https://auto.ria.com/search/",driver.findElement(By.xpath("//div[@class='app-content']//following-sibling::div[@id='topFilter']/a"))),"ERROR:The page was not load");
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

    @Test()
    public void checkHeadersLink(){
        MainPageHeadersLinks link = new MainPageHeadersLinks(driver);
        String expectedTitle = "RIA.com ™ — доска бесплатных частных объявлений Украины";
        Assert.assertTrue(link.checkTheLoadPage().contains(expectedTitle), "\"ERROR: The Ria.come page not loaded");
        AllureLogger.logToAllure(" The Ria.come page was successfully load after clicking on link");
        saveScreenshot(driver);
    }

 }

