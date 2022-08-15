package org.example;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;


/**
 * Unit test for simple App.
 */
public class TC0_Get_ListUsers
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void myFirstGetRequest()
    {
        RestAssured.baseURI = "https://api-football-v1.p.rapidapi.com";

        RequestSpecification httpRequest = RestAssured
                .given()
                .header("X-RapidAPI-Key", "fff0fd0229msh57ca68841e897d2p10e266jsnadcb8497fca3")
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com");
        Response response = httpRequest.request(Method.GET, "/v3/timezone");

        String responseBody = response.getBody().asString();

        //status code validation
        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");
    }
}

