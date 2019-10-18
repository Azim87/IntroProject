package com.kubatov.androidthree.ui.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kubatov.androidthree.R;
import com.kubatov.androidthree.ui.main.MainActivity;

import static com.kubatov.androidthree.Constants.CHANNEL_1;
import static com.kubatov.androidthree.Constants.DESCRIPTION;
import static com.kubatov.androidthree.Constants.MAPBOX;
import static com.kubatov.androidthree.Constants.TITLE;

public class TrackingService extends Service {

    public static final int ID = 1;

    private NotificationManagerCompat managerCompat;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras() != null){
            String title = intent.getStringExtra(TITLE);
            String description = intent.getStringExtra(DESCRIPTION);
            createNotificationChannel();

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            PendingIntent stopNotification = null;
            if (Build.VERSION.SDK_INT >= 26) {
                stopNotification = PendingIntent.getService(this, 1, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            }


            Notification notification = new NotificationCompat.Builder(this, CHANNEL_1)
                    .setSmallIcon(R.drawable.ic_my_location)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setCategory(NotificationCompat.CATEGORY_NAVIGATION)
                    .setColor(Color.RED)
                    .setContentIntent(contentIntent)
                    .addAction(R.drawable.ic_stop, "Stop", stopNotification)
                    .build();
            this.startForeground(ID, notification);

        }else {
            stopForegroundService();
        }

        return START_STICKY;
    }

    private void stopForegroundService(){
        Intent intent = new Intent();
        intent.getFlags();
        stopService(intent);
        this.stopForeground(true);
        stopSelf();
    }

    public void createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_1,
                    MAPBOX,
                    NotificationManager.IMPORTANCE_HIGH );

            notificationChannel.setLightColor(Color.RED);

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(notificationChannel);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}