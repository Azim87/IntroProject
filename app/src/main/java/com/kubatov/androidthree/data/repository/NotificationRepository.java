package com.kubatov.androidthree.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kubatov.androidthree.data.asynktask.InsertAsyncTask;
import com.kubatov.androidthree.data.dao.NotificationDao;
import com.kubatov.androidthree.data.database.NotificationDataBase;
import com.kubatov.androidthree.data.model.notification_model.Notification;

import java.util.List;

public class NotificationRepository {

    private NotificationDao notificationDao;
    private LiveData<List<Notification>> allNotification;

    public NotificationRepository(Application application) {
        NotificationDataBase notificationDataBase = NotificationDataBase.getInstance(application);
        notificationDao = notificationDataBase.notificationDao();
        allNotification = notificationDao.getAll();

    }

    public void insert(Notification notification){
        new InsertAsyncTask(notificationDao).execute(notification);
    }
}
