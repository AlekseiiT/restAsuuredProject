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
        logger.info("*** Started TC0_list_unknown ****");
        RestAssured.baseURI = PropertyUtils.getValue("base_url");
        httpRequest = RestAssured.given();
        response = httpRequest.request(Method.GET, "/api/unknown");
    }

    @Test
    public void checkStatusCode01(){
        logger.info("*** Checking Status Code ***");
        int statusCode = response.getStatusCode();
        logger.info("Status code is " + statusCode);
        Assertions.assertThat(statusCode)
                .isEqualTo(200);
    }
    @Test
    public void checkStatusLine01(){
        logger.info("*** Checking Status Line ***");
        String statusLine = response.getStatusLine();
        logger.info("Status line is " + statusLine);
        Assertions.assertThat(statusLine)
                .isEqualTo("HTTP/1.1 200 OK");
    }
}

