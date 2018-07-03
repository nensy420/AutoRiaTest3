package com.autoRia.pageObjects;

import com.autoRia.statements.AllureLogger;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;


public class MainPageSearchBu {
    private WebDriver driver;


    @FindBy(id = "categories")
    private     WebElement transportField;

    @FindBy(id = "marks")
    private     WebElement brandCarFieldNew;

    @FindBy(id = "models")
    private     WebElement modelCarFieldNew;

    @FindBy(id = "regionCenters")
    private     WebElement regionFieldNew;

    @FindBy(xpath = "//input[@id='brandTooltipBrandAutocompleteInput-brand']/following-sibling::label")
    private   WebElement brandCarFieldBu;

    @FindBy(xpath = "//div[@id='brandTooltipBrandAutocomplete-brand']//ul//following-sibling::li/a")
    private    List<WebElement> brandCarList;

    @FindBy(xpath = "//div[@id='brandTooltipBrandAutocomplete-model']/label")
    private    WebElement modelCarFieldBu;

    @FindBy(xpath = "//div[@id='brandTooltipBrandAutocomplete-model']//ul//following-sibling::li/a")
    private   List<WebElement> modelCarList;

    @FindBy(xpath = "//div[contains(@class,'secondary-column')]//div[@class='m-indent']//label[@for='regionAutocompleteAutocompleteInput-1']")
    private    WebElement regionFieldBu;

    @FindBy(xpath = "//div[@class='autocomplete-search mhide']//ul/li/a")
    private    List<WebElement> regionList;

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

    @FindBy(xpath= "//div[@id='emptyResultsBlock']//div[@class='title' and contains(text(),' К сожалению, по Вашему запросу ')]/span[contains(text(),'Объявлений не найдено')]")
    private    WebElement mainPageSearchErrorMessage;

    @FindBy(xpath = "//div[@class='content']/div[@class='head-ticket']/div[contains(@class,'ticket-title')]/a")
    private List<WebElement> resultList;

    @FindBy(xpath = "//input[@id='buRadioType']/following-sibling::label[@for='buRadioType']")
    private WebElement buRadioButton;

    @FindBy(xpath = "//input[@id='naRadioType']/following-sibling::label[@for='naRadioType']")
    private WebElement newRadioButton;

//    private By popupListElement(String text) {
//        return By.xpath(String.format("//select[@id='marks']/option[contains(text(),'%s')]", text));
//    }



    public MainPageSearchBu(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }



    private WebElement waitTime(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }


    private  void searchTransport(String transportName) {
        Select selectTransportField = new Select(transportField);
        selectTransportField.selectByVisibleText(transportName);
        AllureLogger.logToAllure("Select transport");

    }


    private void searchBrandCarBu(String nameBrandCar) {
        waitTime(brandCarFieldBu).click();
        List<WebElement> listBrandCar = brandCarList;
        for (WebElement elm : listBrandCar)

        {
            String dataValue = elm.getAttribute("innerHTML");

            if (dataValue.contains(nameBrandCar)) {
                waitTime(elm).click();
                AllureLogger.logToAllure("Select car brand");
                return;
            }


        }


    }

    private void searchModelCarBu(String nameModelCar){
        waitTime(modelCarFieldBu).click();
        List<WebElement> listModelCar = modelCarList;
        for (WebElement elm : listModelCar )

        {
            String dataValue = elm.getAttribute("innerHTML");

            if (dataValue.contains(nameModelCar)) {
                waitTime(elm).click();
                AllureLogger.logToAllure("Select car model");
                return;
            }


        }
    }

    private void searchRegionBu(String nameRegion){
        waitTime(regionFieldBu).click();
        List<WebElement> listRegions = regionList;
        for (WebElement elm : listRegions )

        {
            String dataValue = elm.getAttribute("innerHTML");

            if (dataValue.contains(nameRegion)) {
                waitTime(elm).click();
                AllureLogger.logToAllure("Select region");
                return;
            }


        }
    }

    private void selectYear(String from, String to) {
        Select yearSelectFrom = new Select(yearFromField);
        yearSelectFrom.selectByVisibleText(from);
        AllureLogger.logToAllure("Select year from");
        Select yearSelectTo = new Select(yearToField);
        yearSelectTo.selectByVisibleText(to);
        AllureLogger.logToAllure("Select year to");
    }

    private void enterPrice(String priceFrom, String priceTo) {
        priceFromField.sendKeys(priceFrom);
        AllureLogger.logToAllure("Select price from");
        priceToField.sendKeys(priceTo);
        AllureLogger.logToAllure("Select price to");
    }

    public  String resultsOfSearch(){
        List<WebElement> listOfResults = resultList;
        String actualTitle = null ;
        for (WebElement elm : listOfResults) {
            waitTime(elm);
            actualTitle = elm.getAttribute("title");
        }
        AllureLogger.logToAllure("Get actual title");
        return actualTitle;

    }

    public String errorMessage(){
        String attantionMessage = mainPageSearchErrorMessage.getAttribute("innerHTML");
        AllureLogger.logToAllure("Get the message");
        return attantionMessage;
    }


    public void enterSearchParametersBu(String transportName, String brandCarName, String modelName, String regionName, String yearFrom,String yearTo,String priceFrom,String priceTo) {
       buRadioButton.click();
        AllureLogger.logToAllure("Select 'B/U' radio button");
      searchTransport(transportName);
      searchBrandCarBu(brandCarName);
      searchModelCarBu(modelName);
      searchRegionBu(regionName);
      selectYear(yearFrom,yearTo);
      enterPrice(priceFrom,priceTo);
      submit.click();
      AllureLogger.logToAllure("Click on submit button");
    }


}