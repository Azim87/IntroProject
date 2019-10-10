package com.kubatov.androidthree;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import com.kubatov.androidthree.data.preference.SharedPreferenceHelper;

public class App extends Application {
    public static final String CHANNEL_1 = "channel_1";
    private static SharedPreferenceHelper preferenceHelper;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        preferenceHelper = new SharedPreferenceHelper(this);
        context = getApplicationContext();
        createNotificationChannel();

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_1,
                    "mapbox",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("MapBox");
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    public static  SharedPreferenceHelper getPreferenceHelper() {
        return preferenceHelper;
    }

    public static void setPreferenceHelper(SharedPreferenceHelper preferenceHelper) {
        App.preferenceHelper = preferenceHelper;
    }
}
