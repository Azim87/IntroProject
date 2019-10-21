package com.kubatov.androidthree.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.kubatov.androidthree.Constants.CHANNEL_1;
import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class NotificationHelper {
    private static NotificationManagerCompat managerCompat;

    public static Notification showNotification(Context context, int id, Intent intent, int smallIcon, String msg, String title) {
        managerCompat = NotificationManagerCompat.from(getApplicationContext());

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(smallIcon)
                .setAutoCancel(true)
                .build();
        managerCompat.notify(1, notification);
        return notification;
    }
}
