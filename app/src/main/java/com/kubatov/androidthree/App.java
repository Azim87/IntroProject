package com.kubatov.androidthree;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.kubatov.androidthree.data.preference.SharedPreferenceHelper;

public class App extends Application {
    public static final String CHANNEL_1 = "channel_1";
    private NotificationManager notifManager;
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
        if (notifManager == null) {
            notifManager =
                    (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_1,
                    "mapbox",
                    NotificationManager.IMPORTANCE_MAX
            );
            if (notificationChannel == null) {
                notificationChannel.enableVibration(true);
                notificationChannel.setDescription("MapBox");
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
            }
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
