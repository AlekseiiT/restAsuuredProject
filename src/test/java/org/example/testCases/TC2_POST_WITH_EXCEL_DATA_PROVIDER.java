package org.example.testCases;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.example.base.TestBase;
import org.example.propertyUtils.PropertyUtils;
import org.example.utilities.RestUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class TC2_POST_WITH_EXCEL_DATA_PROVIDER extends TestBase {

    @Test(dataProvider = "dataProviderWithExcelWithMap")
    public void TC2_post_query_with_data_provider(Map<String, String> map)
    {
        logger.info("*** Started TC2_POST_WITH_EXCEL_DATA_PROVIDER ****");
        String name = map.get("name");
        String job = map.get("job");

        RestAssured.baseURI = PropertyUtils.getValue("base_url");

        RequestSpecification httpRequest = RestAssured
                .given()
                .header("Content-Type", "application/json");

        JSONObject requestParams = new JSONObject();

        requestParams.put("name", name);
        requestParams.put("job", job);

        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST, "/api/users");

        logger.info("*** Checking Status Code ***");
        int statusCode = response.getStatusCode();
        logger.info("Status code is " + statusCode);
        Assertions.assertThat(statusCode).isEqualTo(201);

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
    }

    @DataProvider
    public Object[][] dataProviderWithExcelWithMap(){
        return RestUtils.getDataFromExcel("/src/test/resources/inputValues.xlsx", "Sheet1");
    }

    @AfterClass
    public void tearDown(){
        logger.info("*** Finished TC2_POST_WITH_EXCEL_DATA_PROVIDER ***");
    }
}
