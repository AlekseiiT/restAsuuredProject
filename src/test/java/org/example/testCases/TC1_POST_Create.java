package org.example.testCases;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.example.propertyUtils.PropertyUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC1_POST_Create {

    @Test
    public void myFirstPostRequest()
    {
        RestAssured.baseURI = PropertyUtils.getValue("base_url");

        RequestSpecification httpRequest = RestAssured
                .given()
                .header("Content-Type", "application/json");

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "morpheus");
        requestParams.put("job", "leader");

        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST, "/api/users");

        System.out.println("Response body is: " + response.getBody().asString());
        //status code validation
        Assert.assertEquals(response.getStatusCode(), 201);

        for (Header header : response.headers()){
            System.out.println(header.getName() + " : " + header.getValue());
        }

        //headers validation
        Assert.assertEquals(response.header("Content-Type"),"application/json; charset=utf-8");
        Assertions.assertThat(Integer.parseInt(response.header("Content-Length")))
                .isBetween(82,86);

        //json validation
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
