package com.kubatov.androidthree.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kubatov.androidthree.data.dao.NotificationDao;
import com.kubatov.androidthree.data.model.notification.Notification;

@Database(entities = Notification.class, version = 1, exportSchema = false)
public abstract class NotificationDatabase extends RoomDatabase {

    public static final String NOTIF_DB = "notif_db";

    private static NotificationDatabase instance;

    public abstract NotificationDao notificationDao();

    public static synchronized NotificationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NotificationDatabase.class, NOTIF_DB)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
