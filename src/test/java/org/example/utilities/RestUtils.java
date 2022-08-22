package org.example.utilities;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class RestUtils {

    public static Object[][] getDataFromExcel(String path, String sheetName){
        try(FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + path))
        {
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheet(sheetName);
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

    public static String genString() {
        return RandomStringUtils.randomAlphabetic(3, 20);
    }

    public static int genInt() {
        return Integer.parseInt(RandomStringUtils.randomNumeric(1, 20));
    }

}
