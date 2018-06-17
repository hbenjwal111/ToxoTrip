package com.toxootrip.api;

/**
 * Created by gagan.mathur on 9/4/2017.
 */
public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://toxotrip.com.cp-in-2.webhostbox.net/api/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
