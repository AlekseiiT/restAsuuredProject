package org.example.testCases;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.example.base.TestBase;
import org.example.propertyUtils.PropertyUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC1_POST_Create extends TestBase {
    @BeforeClass
    public void postCreateAUser(){
        logger.info("**********************Started TC1_post_create_a_user*********************");
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
        Assert.assertEquals(response.getStatusCode(), 201);
    }
    @Test
    public void TC1CheckHeaders(){
        Assert.assertEquals(response.header("Content-Type"),"application/json; charset=utf-8");
        Assertions.assertThat(Integer.parseInt(response.header("Content-Length")))
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
