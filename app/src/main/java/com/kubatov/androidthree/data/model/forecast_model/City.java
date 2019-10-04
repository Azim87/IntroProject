
package com.kubatov.androidthree.data.model.forecast_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kubatov.androidthree.data.model.current_weather.Coord;

public class City {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("coord")
    @Expose
    private Coord coord;



    public Integer id() {
        return id;
    }

    public void setid(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
