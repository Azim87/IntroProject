package com.kubatov.androidthree.data.model.notification;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notification")
public class Notification {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private double lang;
    private double longit;

    public Notification(double lang, double longit) {
        this.lang = lang;
        this.longit = longit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public double getLongit() {
        return longit;
    }

    public void setLongit(double longit) {
        this.longit = longit;
    }
}
