package com.ria.statements;

public class Data {

    @org.testng.annotations.DataProvider
    public Object[][] validDataOfSearch() {
        return new Object[][]{
                {"Легковые", "Audi", "Q7", "Киев", "2014", "2017", "2000", "50000"}};
    }

    @org.testng.annotations.DataProvider
    public Object[] invalidDataOfSearch() {
        return new Object[][]{
                {"Легковые", "Audi", "Q7", "Киев", "2014", "2017", "20000", "50"},
                {"Легковые", "BMW", "X5", "Киев", "2010", "2017", "20000", "10"}
        };
    }
}
