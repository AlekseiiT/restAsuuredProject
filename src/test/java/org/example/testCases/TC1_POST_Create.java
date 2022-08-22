package org.example.testCases;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import org.assertj.core.api.Assertions;
import org.example.base.TestBase;
import org.example.propertyUtils.PropertyUtils;
import org.example.utilities.RestUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC1_POST_Create extends TestBase {

    String name = RestUtils.genString();
    String job = RestUtils.genString();

    @BeforeClass
    public void postCreateAUser(){
        logger.info("*** Started TC1_post_create_a_user ***");
        RestAssured.baseURI = PropertyUtils.getValue("base_url");

        httpRequest = RestAssured
                .given()
                .header("Content-Type", "application/json");

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", name);
        requestParams.put("job", job);
        httpRequest.body(requestParams.toJSONString());

       // System.out.println(RestUtils.genInt());

        response = httpRequest.request(Method.POST, "/api/users");
    }

    @Test
    public void TC1_CheckStatusCode()
    {
        logger.info("*** Checking Status Code ***");
        int statusCode = response.getStatusCode();
        logger.info("Status code is " + statusCode);
        Assertions.assertThat(statusCode)
                .isEqualTo(201);
    }
    @Test
    public void TC1_heckHeaders(){
        logger.info("*** Checking headers ***");
        String contentType = response.header("Content-Type");
        logger.info("Content-type header is " + contentType);
        Assertions.assertThat(contentType)
                .isNotNull()
                .isEqualTo("application/json; charset=utf-8");
    }
    @Test
    public void TC1_JSONValidation(){
        logger.info("*** Checking JSON body values ***");
        String nameValue = (String) response.getBody().jsonPath().get("name");
        logger.info("*** Response value for 'name' is " + nameValue);
        Assertions.assertThat(nameValue)
                .isNotNull()
                .isEqualTo(name);

        String jobValue = (String) response.getBody().jsonPath().get("job");
        logger.info("*** Response value for 'job' is " + jobValue);
        Assertions.assertThat((String) response.getBody().jsonPath().get("job"))
                .isNotNull()
                .isEqualTo(job);

        int idValue = Integer.parseInt(response.getBody().jsonPath().get("id"));
        logger.info("*** Response value for 'id' is " + idValue);
        Assertions.assertThat(idValue)
                .isBetween(1, 10000);

        String createdAtValue = (String) response.getBody().jsonPath().get("createdAt");
        logger.info("*** Response value for 'createdAt' is " + createdAtValue);
        Assertions.assertThat(createdAtValue)
                .isNotNull()
                .hasSize(24);
    }

    @AfterClass
    public void tearDown(){
        logger.info("*** Finished TC1_POST_Create ***");
    }
}
