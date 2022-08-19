package org.example.testCases;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import org.assertj.core.api.Assertions;
import org.example.base.TestBase;
import org.example.propertyUtils.PropertyUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC1_POST_Create extends TestBase {
    @BeforeClass
    public void postCreateAUser(){
        logger.info("*** Started TC1_post_create_a_user ***");
        RestAssured.baseURI = PropertyUtils.getValue("base_url");

        httpRequest = RestAssured
                .given()
                .header("Content-Type", "application/json");

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "morpheus");
        requestParams.put("job", "leader");
        httpRequest.body(requestParams.toJSONString());

        response = httpRequest.request(Method.POST, "/api/users");
    }

    @Test
    public void TC1CheckStatusCode()
    {
        logger.info("*** Checking Status Code ***");
        int statusCode = response.getStatusCode();
        logger.info("Status code is " + statusCode);
        Assertions.assertThat(statusCode)
                .isEqualTo(201);
    }
    @Test
    public void TC1CheckHeaders(){
        logger.info("*** Checking headers ***");
        String contentType = response.header("Content-Type");
        int contentLength = Integer.parseInt(response.header("Content-Length"));
        logger.info("Content-type header is " + contentType);
        logger.info("Content-Length is " + contentLength);
        Assertions.assertThat(contentType)
                .isNotNull()
                .isEqualTo("application/json; charset=utf-8");
        Assertions.assertThat(contentLength)
                .isBetween(82,86);
    }
    @Test
    public void TC1JSONValidation(){
        Assertions.assertThat((String) response.getBody().jsonPath().get("name"))
                .isNotNull()
                .isEqualTo("morpheus");

        Assertions.assertThat((String) response.getBody().jsonPath().get("job"))
                .isNotNull()
                .isEqualTo("leader");

        Assertions.assertThat(Integer.parseInt(response.getBody().jsonPath().get("id")))
                .isBetween(1, 10000);

        Assertions.assertThat((String) response.getBody().jsonPath().get("createdAt"))
                .isNotNull()
                .hasSize(24);
    }
}
