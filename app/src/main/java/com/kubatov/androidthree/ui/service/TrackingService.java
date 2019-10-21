package com.kubatov.androidthree.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

import com.kubatov.androidthree.ui.main.MainActivity;
import com.kubatov.androidthree.util.NotificationHelper;

import static com.kubatov.androidthree.R.drawable.ic_my_location;

public class TrackingService extends Service {

    NotificationManagerCompat managerCompat;
    public static final int ID = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        startForeground(ID, NotificationHelper.showNotification(
                getApplicationContext(),
                1,
                notificationIntent,
                ic_my_location,
                "this is foreground service",
                "Hello!"));

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
