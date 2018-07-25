import com.ria.objects.*;
import com.ria.statements.Data;
import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;


public class TestsAutoRia extends ConfigForTests {

    private static final Logger log = LogManager.getLogger(TestsAutoRia.class);

    @Step("{0}")
    private static void logToAllure(String logger) {
        log.info(logger);
    }

    @Test(dataProvider = "validDataOfSearch",dataProviderClass = Data.class)
    public void checkDownloadPageTest(String transportName, String brandCarName, String modelName, String regionName, String yearFrom, String yearTo, String priceFrom, String priceTo)
    {
        MainPageSearchBu search = new MainPageSearchBu(driver);
        search.enterSearchParametersBu(transportName, brandCarName, modelName,  regionName,  yearFrom, yearTo, priceFrom, priceTo);
        Assert.assertTrue(search.checkIfTHePageDownload("Audi Q7","https://auto.ria.com/search/",driver.findElement(By.xpath("//div[@class='app-content']//following-sibling::div[@id='topFilter']/a"))),"ERROR:The page was not load");
        logToAllure(" The results of search is loaded correctly");
    }

    @Test(dataProvider = "validDataOfSearch",dataProviderClass = Data.class)
    public void checkResultsOfSearchTestBu(String transportName, String brandCarName, String modelName, String regionName, String yearFrom,String yearTo,String priceFrom,String priceTo)
    {
        MainPageSearchBu search = new MainPageSearchBu(driver);
        search.enterSearchParametersBu(transportName, brandCarName, modelName,  regionName,  yearFrom, yearTo, priceFrom, priceTo);
        String expectedTitle = "Audi Q7 ghj";
        Assert.assertTrue(search.resultsOfSearch().contains(expectedTitle), " The results of search is not loaded");
        logToAllure(" The results of search is loaded correctly");
    }

    @Test(dataProvider = "invalidDataOfSearch",dataProviderClass = Data.class)
    public void invalidDataSearchTest (String transportName, String brandCarName, String modelName, String regionName, String yearFrom,String yearTo,String priceFrom,String priceTo)
    {
        MainPageSearchBu search = new MainPageSearchBu(driver);
        search.enterSearchParametersBu(transportName, brandCarName, modelName,  regionName,  yearFrom, yearTo, priceFrom, priceTo);
        String expectedErrorMessage ="Объявлений не найдено";
        String actualErrorMessage = search.errorMessage();
        System.out.println(actualErrorMessage);
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage)," The error message wasn't load");
        logToAllure(" The error message is displayed");
    }

    @Test()
    public void registrationWithInvalidDataTest() {
        AuthorizationCheck check = new AuthorizationCheck(driver);
        String expectedErrorMessage = "неверный  мобильный номер телефона";
        check.registration("236lo659");
        String actualErrorMessage = check.getErrorMessageRegistration();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), " The registration with invalid data was successful");
        logToAllure(" The registration was failed");
    }

    @Test()
    public void checkResultsOfSearchTestNew() {
        MainPageSearchNewCars search = new MainPageSearchNewCars(driver);
        search.enterSearchParametersNew("Легковые", "BMW", "X5", "Киев", "2010", "2017", "2000", "100000");
        String expectedResult = "newauto";
        Assert.assertTrue(search.resultsOfSearch().contains(expectedResult), " The search page was not load");
        logToAllure(" The results of search is loaded correctly");
    }

    @Test()
    public void checkHeadersLink(){
        MainPageHeadersLinks link = new MainPageHeadersLinks(driver);
        String expectedTitle = "RIA.com ™ — доска бесплатных частных объявлений Украины";
        Assert.assertTrue(link.checkTheLoadPage().contains(expectedTitle), " The Ria.come page not loaded");
        logToAllure(" The Ria.come page was successfully load after clicking on link");
    }


 }

