package com.herokuapp.restful.booker.here.commonUtils;

public class EnvironmentProperties {
    public String AUTH_URL;

    public String getAuthURL() {
        return AUTH_URL;
    }

    public void setAuthURL(String AUTH_URL) {
        this.AUTH_URL = AUTH_URL;
    }

    public String getHostName() {
        return HOST_NAME;
    }

    public void setHostName(String HOST_NAME) {
        this.HOST_NAME = HOST_NAME;
    }

    public String getUserName() {
        return USER_NAME;
    }

    public void setUserName(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public void setPassword(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String HOST_NAME;
    public String USER_NAME;
    public String PASSWORD;
}
