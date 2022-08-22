package org.example.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class Listeners extends TestListenerAdapter {

    public ExtentReports extent;
    public ExtentTest test;

    public void onStart(ITestContext testContext)
    {
        ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/myReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("MyRestAssuredReport");
        spark.config().setReportName("Test Report");
    }

    public void onTestSuccess(ITestResult result){
        test = extent.createTest(result.getName());
        test.log(Status.PASS, "Test Case Passed is " + result.getName());
    }

    public void onTestFailure(ITestResult result){
        test = extent.createTest(result.getName());

        test.log(Status.FAIL, "Test case failed is " + result.getName());
        test.log(Status.FAIL, "Test case failed is " + result.getThrowable());
    }

    public void onTestSkipped(ITestResult result){
        test = extent.createTest(result.getName());
        test.log(Status.SKIP, "Test Case skipped is " + result.getName());
    }

    public void onFinish(ITestContext testContext){
        extent.flush();
    }
}
