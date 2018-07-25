package com.ria.objects;


import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;

public class MainPageSearchNewCars {
    private WebDriver driver;

    @FindBy(id = "categories")
    private WebElement transportField;

    @FindBy(xpath = "//input[@id='brandTooltipBrandAutocompleteInput-brand']/following-sibling::label[@for='brandTooltipBrandAutocompleteInput-brand']")
    private     WebElement brandCarFieldNew;

    @FindBy(xpath = "//div[@id='brandTooltipBrandAutocomplete-model']/label")
    private     WebElement modelCarFieldNew;

    @FindBy(id = "regionCenters")
    private     WebElement regionFieldNew;

    @FindBy(xpath = "//div[@id='brandTooltipBrandAutocomplete-brand']//ul//following-sibling::li/a")
    private List<WebElement> brandCarList;

    @FindBy(xpath = "//div[@id='brandTooltipBrandAutocomplete-model']//ul//following-sibling::li/a")
    private   List<WebElement> modelCarList;

    @FindBy(id = "yearFrom")
    private    WebElement yearFromField;

    @FindBy(id = "yearTo")
    private    WebElement yearToField;

    @FindBy(id = "priceFrom")
    private    WebElement priceFromField;

    @FindBy(id = "priceTo")
    private    WebElement priceToField;

    @FindBy(xpath = "//form[@id='mainSearchForm']/div[@class='footer-form']/button")
    private   WebElement submit;

    @FindBy(xpath = "//input[@id='naRadioType']/following-sibling::label[@for='naRadioType']")
    private WebElement newRadioButton;

    @FindBy(xpath = "//div[@id='searchResult']//a[contains(@href,'newauto')]")
    private List<WebElement> resultList;

    @FindBy(xpath = "//dd[@class='item']//span[contains(text(),'Год выпуска')]//following-sibling::span")
    private List<WebElement> characteristic;

    private static final Logger log = LogManager.getLogger(MainPageSearchNewCars.class);

    @Step("{0}")
    private static void logToAllure(String logger) {
        log.info(logger);
    }

    private WebElement waitTime(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    private  void searchTransport(String transportName) {
        Select selectTransportField = new Select(transportField);
        selectTransportField.selectByVisibleText(transportName);
        logToAllure("Select transport");

    }

    private  void searchBrandCarNew(String brandName) {
        waitTime(brandCarFieldNew).click();
        List<WebElement> listBrandCar = brandCarList;
        for (WebElement elm : listBrandCar)
        {
            String dataValue = elm.getAttribute("innerHTML");

            if (dataValue.contains(brandName)) {
                waitTime(elm).click();
                logToAllure("Select car brand");
                return;
            }
        }
    }

    private  void searchModelCarNew(String modelName) {
        waitTime(modelCarFieldNew).click();
        List<WebElement> listModelCar = modelCarList;
        for (WebElement elm : listModelCar )
        {
            String dataValue = elm.getAttribute("innerHTML");

            if (dataValue.contains(modelName)) {
                waitTime(elm).click();
                logToAllure("Select car model");
                return;
            }
        }
    }

    private void searchRegionNew(String regionName) {
        Select selectTransportField = new Select(regionFieldNew);
        selectTransportField.selectByVisibleText(regionName);
        logToAllure("Select region");
    }

    private void selectYear(String from, String to) {
        Select yearSelectFrom = new Select(yearFromField);
        yearSelectFrom.selectByVisibleText(from);
        logToAllure("Select year from");
        Select yearSelectTo = new Select(yearToField);
        yearSelectTo.selectByVisibleText(to);
        logToAllure("Select year to");
    }

    private void enterPrice(String priceFrom, String priceTo) {
        priceFromField.sendKeys(priceFrom);
        logToAllure("Select price from");
        priceToField.sendKeys(priceTo);
        logToAllure("Select price to");
    }

    public MainPageSearchNewCars(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public  String resultsOfSearch(){
        List<WebElement> listOfResults = resultList;
        String actualTitle = null ;
        for (WebElement elm : listOfResults) {
            waitTime(elm);
            actualTitle = elm.getAttribute("href");
        }
        logToAllure("Get actual title");
        return actualTitle;
    }

    public void enterSearchParametersNew(String transportName, String brandCarName, String modelName, String regionName, String yearFrom,String yearTo,String priceFrom,String priceTo) {
        newRadioButton.click();
        logToAllure("Select 'New Auto' radio button");
        searchTransport(transportName);
        searchBrandCarNew(brandCarName);
        searchModelCarNew(modelName);
        searchRegionNew(regionName);
        selectYear(yearFrom,yearTo);
        enterPrice(priceFrom,priceTo);
        submit.click();
        logToAllure("Click on submit button");
    }
}
