package com.kubatov.androidthree.data.network;

import com.kubatov.androidthree.data.model.current_weather.CurrentWeather;
import com.kubatov.androidthree.data.model.forecast_model.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroFitService {

    public static final String WEATHER = "weather";
    public static final String FORECAST = "forecast";

    @GET(WEATHER)
        Call<CurrentWeather> getWeatherByName(
            @Query("q") String city,
            @Query("lang") String lang,
            @Query("APPID") String key,
            @Query("units") String unit);

    @GET(FORECAST)
        Call<Forecast> getForecast(
            @Query("q") String city,
            @Query("lang") String lang,
            @Query("APPID") String key,
            @Query("units") String unit);
}
