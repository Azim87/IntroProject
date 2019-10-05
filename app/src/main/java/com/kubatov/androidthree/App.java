package com.kubatov.androidthree;

import android.app.Application;
import android.content.Context;

import com.kubatov.androidthree.data.preference.SharedPreferenceHelper;

public class App extends Application {

    public static Context context;
    private static SharedPreferenceHelper preferenceHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        preferenceHelper = new SharedPreferenceHelper(this);

    }

    public static  SharedPreferenceHelper getPreferenceHelper() {
        return preferenceHelper;
    }

    public static void setPreferenceHelper(SharedPreferenceHelper preferenceHelper) {
        App.preferenceHelper = preferenceHelper;
    }
}
