package com.kubatov.androidthree.data.network;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetroFitBuilder {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static RetroFitBuilder retroFitBuilder;
    private static RetroFitService retroFitService;

    public static RetroFitService getService() {
        if (retroFitService == null) {
            retroFitService = buildRetroFit();
        }
        return retroFitService;
    }

    private static RetroFitService buildRetroFit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetroFitService.class);
    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

}
