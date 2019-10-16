package com.kubatov.androidthree.data.asynktask;

import android.os.AsyncTask;

import com.kubatov.androidthree.data.dao.NotificationDao;
import com.kubatov.androidthree.data.model.notification.Notification;

public class InsertAsyncTask extends AsyncTask<Notification,Void, Void> {

    private NotificationDao mNoteDao;

    public InsertAsyncTask(NotificationDao noteDao){
        mNoteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Notification... notifications) {
        mNoteDao.insert(notifications[0]);
        return null;
    }
}