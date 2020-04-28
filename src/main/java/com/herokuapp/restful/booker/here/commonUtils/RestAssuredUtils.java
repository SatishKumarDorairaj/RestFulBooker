package com.herokuapp.restful.booker.here.commonUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

public class RestAssuredUtils{

private static String token;

    public   void  tokenAuthentication(String userName , String password , String url) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", userName);
        jsonObject.put("password", password);
        try {
            Response response = RestAssured.given()
                    .body(jsonObject.toString())
                    .contentType("application/json")
                    .when()
                    .post(url);;
                    token = response.getBody().jsonPath().get("token");
            System.out.println("Token is  " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  Response get(String URL) {
        try {
            Response response = RestAssured.given()
                    .contentType("application/json")
                    .accept("application/json")
                    .when()
                    .get(URL);
            System.out.println("Response fetched for the get request is : "+response.prettyPrint());
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    public  Response delete(String URL) {
        try {
            Response response = RestAssured.given()
                    .contentType("application/json")
                    .accept("application/json")
                    .header("Cookie","token="+token)
                    .when()
                    .delete(URL);
            System.out.println("Response fetched for the delete request is : "+response.prettyPrint());
            return response;
        } catch (Exception e) {
            return null;
        }
    }


    public  Response post(String URL , Object json) {
        System.out.println("URl given for Post request is : "+URL);
        System.out.println("Payload for the post request is  : "+json.toString());
        try {
            Response response = RestAssured.given()
                    .contentType("application/json")
                    .accept("application/json")
                    .body(json.toString())
                    .when()
                    .post(URL);
            System.out.println("Response fetched for the post request is : "+response.prettyPrint());
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    public  Response put(String URL , Object json) {
        // tokenAuthentication("admin","password123","https://restful-booker.herokuapp.com/auth");
        System.out.println("URl given for Put request is : "+URL);
        System.out.println("Payload for the Put request is  : "+json.toString());
        System.out.println("Token for PUT is  : "+token);
        try {
            Response response = RestAssured.given()
                    .contentType("application/json")
                    .accept("application/json")
                    .header("Cookie","token="+token)
                    .body(json.toString())
                    .when()
                    .put(URL);
            System.out.println("Response fetched for the Put request is : "+response.prettyPrint());
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    public  Response patch(String URL , Object json) {
        // tokenAuthentication("admin","password123","https://restful-booker.herokuapp.com/auth");
        System.out.println("URl given for patch request is : "+URL);
        System.out.println("Payload for the patch request is  : "+json.toString());
        System.out.println("Token for patch is  : "+token);
        try {
            Response response = RestAssured.given()
                    .contentType("application/json")
                    .accept("application/json")
                    .header("Cookie","token="+token)
                    .body(json.toString())
                    .when()
                    .patch(URL);
            System.out.println("Response fetched for the patch request is : "+response.prettyPrint());
            return response;
        } catch (Exception e) {
            return null;
        }
    }

}
