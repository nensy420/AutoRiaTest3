package com.ria.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.AssertJUnit;
import static com.jayway.restassured.RestAssured.get;
import com.jayway.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;

public class Api {
    private WebDriver driver;

    private String apiKey = "Q7qNVxdwbZV6xGmpD37IjPjy0AuCRmkYK5lNnRpd";
    private String parameters="categories.main.id=1&price.currency=1&price.USD.gte=5000&price.USD.lte=100000&region.id[0]=1&brand.id[0]=6&model.id[0]=1943&year[0].gte=2010&year[0].lte=2018";


    public Api(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void getRequest() throws IOException {
        HttpClient client = new DefaultHttpClient();
//        HttpGet request = new HttpGet("https://developers.ria.com/auto/categories/?api_key="+apiKey+"");
        HttpGet request = new HttpGet("https://developers.ria.com/auto/search?api_key="+apiKey+"&"+parameters+"");
        HttpResponse response = client.execute(request);
        int actualStatusCode = response.getStatusLine().getStatusCode();
        System.out.println(actualStatusCode);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }



    }
    public void getRequestWithRestAssure()throws JSONException{
        Response resp = get("https://developers.ria.com/auto/search?api_key="+apiKey+"&"+parameters+"");
        JsonParser parser = new JsonParser();
        JsonElement jsonResponse = parser.parse(resp.body().asString());
        System.out.println(jsonResponse);
      //  String capital = jsonResponse.getJSONObject(0).getString("capital");
    //   AssertJUnit.assertEquals(capital, "Kiev");

    }
}
