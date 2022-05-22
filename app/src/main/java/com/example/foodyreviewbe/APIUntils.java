package com.example.foodyreviewbe;

public class APIUntils {
    public static final String BASE_URL = "http://huyhung.online/";

    public static APIService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
