
package com.kubatov.androidthree.data.model.forecast_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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

}
