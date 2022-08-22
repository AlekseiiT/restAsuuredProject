package org.example.testCases;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import org.assertj.core.api.Assertions;
import org.example.base.TestBase;
import org.example.propertyUtils.PropertyUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC3_POST_Registration_Success extends TestBase {

    @BeforeClass
    public void postRegesterUserSuccess(){

        logger.info("*** Started TC3_POST_Registration_Success ****");
        RestAssured.baseURI = PropertyUtils.getValue("base_url");

        httpRequest = RestAssured
                .given()
                .header("Content-Type", "application/json");

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "eve.holt@reqres.in");
        requestParams.put("password", "pistol");

        httpRequest.body(requestParams.toJSONString());

        response = httpRequest.request(Method.POST, "api/register");
    }

    @Test
    public void TC3_CheckStatusCode(){
        logger.info("*** Checking Status Code ***");
        int statusCode = response.getStatusCode();
        logger.info("Status code is " + statusCode);
        Assertions.assertThat(statusCode)
                .isEqualTo(200);
    }

    @Test
    public void TC3_CheckHeaders(){
        logger.info("*** Checking headers ***");
        String contentType = response.header("Content-Type");
        logger.info("Content-type header is " + contentType);
        Assertions.assertThat(contentType)
                .isNotNull()
                .isEqualTo("application/json; charset=utf-8");
    }

    @Test
    public void TC3_JSONValidation() {
        logger.info("*** Checking JSON body values ***");
        int idValue = response.getBody().jsonPath().getInt("id");
        logger.info("*** Response value for 'id' is " + idValue);
        Assertions.assertThat(idValue)
                .isBetween(1, 10000);

        String tokenValue = response.getBody().jsonPath().getString("token");
        logger.info("*** Response value for 'token' is " + tokenValue);
        Assertions.assertThat(tokenValue)
                .isNotNull()
                .hasSize(17);
    }

    @AfterClass
    public void tearDown(){
        logger.info("*** Finished TC3_POST_Registration_Success ***");
    }
}
