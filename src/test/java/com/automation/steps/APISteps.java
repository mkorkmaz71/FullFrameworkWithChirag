package com.automation.steps;

import com.automation.utils.ConfigReader;
import com.automation.utils.RestAssuredUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class APISteps {
    @Given("user set up request for {string} end point")
    public void userSetUpRequestForEndPoint(String endpoint) {
        RestAssuredUtils.setEndpoint(endpoint);
    }

    @When("user perform GET request")
    public void userPerformGETRequest() {
        RestAssuredUtils.get();
    }

    @When("user perform POST request")
    public void userPerformPOSTRequest() {
        RestAssuredUtils.post();
    }

    @When("user perform PUT request")
    public void userPerformPUTRequest() {
        RestAssuredUtils.put();
    }

    @Then("verify status code is {int}")
    public void verifyStatusCodeIs(int statusCode) {
        Assert.assertEquals(statusCode, RestAssuredUtils.getStatusCode());
    }

    @And("set header value {string} to {string}")
    public void setHeaderValueTo(String key, String value) {
        RestAssuredUtils.setHeader(key,value);
    }

    public static String readDataFromFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\\Z");
        return sc.next();
    }


    @And("verify response body has a token")
    public void verifyResponseBodyHasAToken() {
        String token=RestAssuredUtils.getResponseField("authToken");
        Assert.assertFalse(token.isEmpty());
        System.out.println("Token=====>"+token);
        ConfigReader.setProperty("auth.token",token);
    }

    @And("set query parameter {string} to {string}")
    public static void setQueryParam(String param, String value) {
        RestAssuredUtils.setQueryParam(param,value);
    }

    @And("set body for the create user request")
    public void setBodyForTheCreateUserRequest() throws FileNotFoundException {
        String body=readDataFromFile("src/test/resources/data/CreateUserBody.json");
        body=body.replace("email_address_value",getRandomEmail());
        body=body.replace("ssn_value",getRandomSSN());
        RestAssuredUtils.setBody(body);
    }
    public static String getRandomEmail(){
        String email="TestAutomation"+getRandomSSN()+"@demo.com";
        ConfigReader.setProperty("api.email",email);
        return email;
    }
    public static String getRandomSSN(){
        Random random=new Random();
        String ssn=String.valueOf(100000000+random.nextInt(900000000));
        ConfigReader.setProperty("api.ssn",ssn);
        return ssn;
    }

    @And("set token for authorization")
    public void setTokenForAuthorization() {
        String token=ConfigReader.getProperty("auth.token");
        RestAssuredUtils.setHeader("Authorization","Bearer "+token);
    }
}
