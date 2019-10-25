package com.kubatov.androidthree.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


public class SharedPreferenceHelper {

    private final static String PREFERENCE = "mePref";
    private final static String IS_FIRST_LAUNCH = "isFirstLaunch";
    private static final String DISTANCE = "distance";
    private static final String DURATION = "duration";
    private SharedPreferences preferences;

    public SharedPreferenceHelper(Context context){
       preferences = context.getSharedPreferences(PREFERENCE, MODE_PRIVATE);
    }

    public boolean isFirstLaunch(){
       return preferences.getBoolean(IS_FIRST_LAUNCH, true);
    }

    public void setFirstLaunch(){
        preferences.edit().putBoolean(IS_FIRST_LAUNCH, false).apply();
    }

    public void setDistances(float putDistance){
        preferences.edit().putFloat(DISTANCE, putDistance).apply();
    }

    public void setDurations(float setDuration){
        preferences.edit().putFloat(DURATION, setDuration).apply();
    }

    public float getDistances(){
        return preferences.getFloat(DISTANCE, 0.0f);
    }

    public float getDurations(){
        return preferences.getFloat(DURATION, 0.0f);
    }
}
