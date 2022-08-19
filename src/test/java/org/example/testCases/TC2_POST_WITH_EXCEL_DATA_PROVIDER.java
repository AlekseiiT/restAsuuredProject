package org.example.testCases;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.Assertions;
import org.example.base.TestBase;
import org.example.propertyUtils.PropertyUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class TC2_POST_WITH_EXCEL_DATA_PROVIDER extends TestBase {

    @Test(dataProvider = "dataProviderWithExcelWithMap")
    public void post_query_with_data_provider(Map<String, String> map)
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

        try(FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/inputValues.xlsx"))
        {
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheet("Sheet1");
            DataFormatter dataFormatter = new DataFormatter();

            int rowNum = sheet.getLastRowNum();
            int columnNum = sheet.getRow(0).getLastCellNum();

            Object[][] data = new Object[rowNum][1];
            Map<String, String> map;

            for (int i = 1; i <= rowNum; i++) {
                map = new HashMap<>();
                for (int j = 0; j < columnNum; j++) {
                    String key = dataFormatter.formatCellValue(sheet.getRow(0).getCell(j));
                    String value = dataFormatter.formatCellValue(sheet.getRow(i).getCell(j));
                    map.put(key, value);
                }
                data[i-1][0] = map;
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
