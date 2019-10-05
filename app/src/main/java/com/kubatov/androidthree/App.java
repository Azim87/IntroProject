package com.kubatov.androidthree;

import android.app.Application;
import android.content.Context;

import com.kubatov.androidthree.data.preference.SharedPreferenceHelper;

public class App extends Application {
    private static SharedPreferenceHelper preferenceHelper;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        preferenceHelper = new SharedPreferenceHelper(this);
        context = getApplicationContext();

    }

    public static  SharedPreferenceHelper getPreferenceHelper() {
        return preferenceHelper;
    }

    public static void setPreferenceHelper(SharedPreferenceHelper preferenceHelper) {
        App.preferenceHelper = preferenceHelper;
    }
}
