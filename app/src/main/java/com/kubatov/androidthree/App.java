package com.kubatov.androidthree;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.kubatov.androidthree.data.preference.SharedPreferenceHelper;

import static com.kubatov.androidthree.Constants.CHANNEL_1;
import static com.kubatov.androidthree.Constants.MAPBOX;

public class App extends Application {

    private static SharedPreferenceHelper preferenceHelper;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        preferenceHelper = new SharedPreferenceHelper(this);
        context = getApplicationContext();
        createNotificationChannel();
    }

    private void createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_1, MAPBOX,
                    NotificationManager.IMPORTANCE_HIGH );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(notificationChannel);
            }
        }
    }

    public static  SharedPreferenceHelper getPreferenceHelper() {
        return preferenceHelper;
    }
}
