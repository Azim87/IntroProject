package com.kubatov.androidthree.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kubatov.androidthree.data.model.notification.Notification;

import java.util.List;

@Dao
public interface NotificationDao {

    @Insert
    void insert(List<Notification> notification);

    @Update
    void update(Notification notification);

    @Delete
    void delete(Notification notification);


    @Query("SELECT * FROM notification")
    LiveData<List<Notification>> getAllNotification();


}
