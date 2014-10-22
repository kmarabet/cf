package com.cf.controller.security.authentication;


public class AuthResult {
    private int httpStatusCode;
    private String message;
    private String userName;
    private String institutionName;
    private String homePage;

    public AuthResult(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public AuthResult(int httpStatusCode, String message, String userName, String homePage) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.userName = userName;
        this.homePage = homePage;
    }

}
