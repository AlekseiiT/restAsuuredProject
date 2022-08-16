package org.example;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.propertyUtils.PropertyUtils;
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
        RestAssured.baseURI = PropertyUtils.getValue("base_url");

        RequestSpecification httpRequest = RestAssured
                .given()
                .header("X-RapidAPI-Key", PropertyUtils.getValue("secret_key"))
                .header("X-RapidAPI-Host", PropertyUtils.getValue("api_host"));
        Response response = httpRequest.request(Method.GET, "/v3/timezone");

        //status code validation
        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");
    }
}

