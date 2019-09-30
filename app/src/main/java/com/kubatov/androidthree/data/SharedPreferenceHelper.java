package com.kubatov.androidthree.data;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


public class SharedPreferenceHelper {

    private final static String PREFERENCE = "mePref";
    private final static String IS_FIRST_LAUNCH = "isFirstLaunch";
    private SharedPreferences preferences;

    public SharedPreferenceHelper(Context context){
       preferences = context.getSharedPreferences(PREFERENCE, MODE_PRIVATE);
    }

    public boolean isFirstLaunch(){
       return preferences.getBoolean(IS_FIRST_LAUNCH, false);
    }

    public void setFirstLaunch(){
        preferences.edit().putBoolean(IS_FIRST_LAUNCH, false).apply();
    }
}
