
package com.kubatov.androidthree.data.model.forecast_model;

import com.kubatov.androidthree.data.model.current_weather.Clouds;
import com.kubatov.androidthree.data.model.current_weather.Main;
import com.kubatov.androidthree.data.model.current_weather.Sys;
import com.kubatov.androidthree.data.model.current_weather.Wind;

import java.util.List;

public class Mylist {

    public int dt ;
    public Main main ;
    public List<Weather> weather;
    public Clouds clouds ;
    public Wind wind ;
    public Sys sys ;
    public String dt_txt;
    public Rain rain ;

    public Mylist(int dt, Main main, List<Weather> weather, Clouds clouds, Wind wind, Sys sys, String dt_txt, Rain rain) {
        this.dt = dt;
        this.main = main;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
        this.sys = sys;
        this.dt_txt = dt_txt;
        this.rain = rain;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }
}
