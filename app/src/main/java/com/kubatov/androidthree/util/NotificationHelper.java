package com.kubatov.androidthree.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class NotificationHelper {
    private static NotificationManagerCompat managerCompat;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Notification showNotification(Context context, int id, Intent intent, int smallIcon, String msg, String title, String channel) {
        managerCompat = NotificationManagerCompat.from(getApplicationContext());

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), Notification.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(smallIcon)
                .setAutoCancel(true)
                .setChannelId(channel)
                .build();
        managerCompat.notify(1, notification);
        return notification;
    }
}
