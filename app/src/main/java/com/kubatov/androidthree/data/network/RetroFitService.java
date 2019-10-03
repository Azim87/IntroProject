package com.kubatov.androidthree.data.network;

import com.kubatov.androidthree.data.model.current_weather.CurrentWeather;
import com.kubatov.androidthree.data.model.forecast_model.Forecast;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroFitService {

        @GET("weather")
        Call<CurrentWeather> getWeatherByName(
                @Query("q") String city,
                @Query("APPID") String key,
                @Query("units") String unit
        );

        @GET("forecast")
        Call<List<Forecast>> getForecast(
                @Query("q") String city,
                @Query("cnt") int days,
                @Query("APPID") String key,
                @Query("units") String unit
        );

}
