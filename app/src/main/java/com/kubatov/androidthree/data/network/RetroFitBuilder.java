package com.kubatov.androidthree.data.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kubatov.androidthree.BuildConfig.WEATHER_BASE_URL;


public class RetroFitBuilder {

    private static RetroFitService retroFitService;

    public static RetroFitService getService() {
        if (retroFitService == null) {
            retroFitService = buildRetroFit();
        }
        return retroFitService;
    }

    private static RetroFitService buildRetroFit() {
        return new Retrofit.Builder()
                .baseUrl(WEATHER_BASE_URL)
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
