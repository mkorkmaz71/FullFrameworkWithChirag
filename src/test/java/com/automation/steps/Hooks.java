package com.automation.steps;

import com.automation.utils.ConfigReader;
import com.automation.utils.DatabaseUtils;
import com.automation.utils.DriverUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class Hooks {

    @Before("@ui")
    public void setUp() {
        ConfigReader.initProperties();
        DriverUtils.createDriver();
        DatabaseUtils.initDatabase();
    }
    @Before("@api")
    public  void setUpAPI(){
        ConfigReader.initProperties();
        DatabaseUtils.initDatabase();
        RestAssured.baseURI=ConfigReader.getProperty("api.url");
    }

    @After("@ui")
    public void cleanUp() {
        DriverUtils.getDriver().quit();
    }

}
