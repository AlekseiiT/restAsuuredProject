package org.example;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.example.propertyUtils.PropertyUtils;
import org.testng.Assert;
import org.testng.annotations.Test;


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
                .given();
        Response response = httpRequest.request(Method.GET, "/api/unknown");

        System.out.println("Response body is: " + response.getBody().asString());
        //status code validation

        Assertions.assertThat(response.getStatusCode())
                        .isEqualTo(200);
        Assertions.assertThat(response.getStatusLine())
                        .isEqualTo("HTTP/1.1 200 OK");
    }
}

