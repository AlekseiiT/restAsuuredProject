package org.example;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.propertyUtils.PropertyUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class TC2_POST_WITH_EXCEL_DATA_PROVIDER {

    @Test(dataProvider = "dataProviderWithExcelWithMap")
    public void post_query_with_data_provider(Map<String, String> map)
    {
        RestAssured.baseURI = PropertyUtils.getValue("base_url");

        RequestSpecification httpRequest = RestAssured
                .given()
                .header("Content-Type", "application/json");

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", map.get("name"));
        requestParams.put("job", map.get("job"));

        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST, "/api/users");

        System.out.println("Response body is: " + response.getBody().asString());
        //status code validation
        Assert.assertEquals(response.getStatusCode(), 201);
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
