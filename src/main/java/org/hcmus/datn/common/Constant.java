package org.hcmus.datn.common;

public class Constant {
//    public static final String SONARQUBE_HOST = "https://af2c-54-151-141-45.ap.ngrok.io";
//    public static final String SONARQUBE_USERNAME = "admin";
//    public static final String SONARQUBE_PASSWORD = "123456";
    public static final String GENERATE_TOKEN_API = Config.get("SONARQUBE_HOST") + "/api/user_tokens/generate";
//    public static final String GET_RESULT_API = SONARQUBE_HOST + "/api/issues/search?componentKeys=%s&types=%s";
    public static final String GET_RESULT_API = Config.get("SONARQUBE_HOST") + "/api/issues/search";
    public static final String[] ISSUE_TYPES = { "CODE_SMELL", "BUG", "VULNERABILITY"};



    public static final String DATABASE_SERVICE_RESPONSE_ERROR = "error";
    public static final String DATABASE_SERVICE_RESPONSE_DATA = "data";

}
