package com.herokuapp.restful.booker.here.testScripts;

import com.herokuapp.restful.booker.here.commonUtils.EnvironmentProperties;
import com.herokuapp.restful.booker.here.commonUtils.RestAssuredUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import org.testng.Assert;

import java.io.*;


public class BaseTest {

    public EnvironmentProperties environmentProperties;
    RestAssuredUtils restAssuredUtils = new RestAssuredUtils();

    public BaseTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            environmentProperties = objectMapper.readValue(new File("environmentDetails.json"), EnvironmentProperties.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        restAssuredUtils.tokenAuthentication(environmentProperties.getUserName(), environmentProperties.getPassword(), environmentProperties.getAuthURL());
        healthCheckOfHost();
    }

    public void healthCheckOfHost() {
        Response response = restAssuredUtils.get(environmentProperties.getHostName() + "ping");
        Assert.assertEquals(response.getStatusCode(), 201, "Health check failed for the HOST...please check!!!");
        System.out.println("Health check is successfully completed");
    }

}
