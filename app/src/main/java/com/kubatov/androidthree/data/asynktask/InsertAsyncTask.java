package com.kubatov.androidthree.data.asynktask;

import android.os.AsyncTask;
import com.kubatov.androidthree.data.dao.NotificationDao;
import com.kubatov.androidthree.data.model.notification_model.Notification;

public class InsertAsyncTask extends AsyncTask<Notification, Void, Void> {

    private NotificationDao mNotificationDao;

    public InsertAsyncTask(NotificationDao notificationDao) {
        mNotificationDao = notificationDao;
    }

    @Override
    protected Void doInBackground(Notification... notifications) {
        mNotificationDao.insert(notifications[0]);
        return null;
    }
}