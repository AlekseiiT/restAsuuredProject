package org.example.testCases;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import org.assertj.core.api.Assertions;
import org.example.base.TestBase;
import org.example.propertyUtils.PropertyUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC0_Get_ListUsers extends TestBase
{
    @BeforeClass
    public void get_list()
    {
        logger.info("**********************Started TC0_list_unknown*********************");
        RestAssured.baseURI = PropertyUtils.getValue("base_url");
        httpRequest = RestAssured.given();
        response = httpRequest.request(Method.GET, "/api/unknown");
    }

    @Test
    public void checkStatusCode01(){
        System.out.println(response.getBody().asString());
        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }
    @Test
    public void checkStatusLine01(){
        Assertions.assertThat(response.getStatusLine())
                .isEqualTo("HTTP/1.1 200 OK");
    }
}

