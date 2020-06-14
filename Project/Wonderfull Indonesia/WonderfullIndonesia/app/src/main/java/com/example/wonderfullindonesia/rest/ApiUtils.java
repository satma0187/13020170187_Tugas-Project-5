package com.example.wonderfullindonesia.rest;

public class ApiUtils {
    public static final String BASE_URL = "http://192.168.43.137/wonderfullrestapi/api/";

    public static ApiService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
