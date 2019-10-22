package com.kubatov.androidthree.ui.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.kubatov.androidthree.data.model.notification.Notification;
import com.kubatov.androidthree.ui.main.MainActivity;
import com.kubatov.androidthree.util.NotificationHelper;

import java.util.ArrayList;

import static com.kubatov.androidthree.Constants.CHANNEL_1;
import static com.kubatov.androidthree.R.drawable.ic_my_location;

public class TrackingService extends Service {
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private Location mLocation;
    private ArrayList<Notification> mNotificationList;

    NotificationManagerCompat managerCompat;
    public static final int ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        requestPermission();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    initFusedLocation();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startForeground(ID, NotificationHelper.showNotification(
                    getApplicationContext(),
                    1,
                    notificationIntent,
                    ic_my_location,
                    "this is foreground service",
                    "Hello!",
                    CHANNEL_1));
        }

        return START_STICKY;
    }

    private void initFusedLocation(){
        createLocationCallback();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = LocationRequest.create();
        requestUpdates();
    }

    private void createLocationCallback(){
        mNotificationList = new ArrayList<>();

        mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mLocation = locationResult.getLastLocation();
                Log.d("ololo", "onLocationResult: " + mLocation.getLatitude() + " " + mLocation.getLongitude());
                mNotificationList.add(new Notification(
                        mLocation.getLatitude(),
                        mLocation.getLongitude()

                ));
                locationUpdated();
            }
        };
    }

    private void locationUpdated(){
        mLocationCallback = new LocationCallback();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setSmallestDisplacement(4.0f);
    }

    private void requestPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Not Enough Permission", Toast.LENGTH_SHORT).show();
            locationUpdated();
            return;
        }
    }

    private void requestUpdates(){
     mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper());
    }

    private void removeUpdates(){
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeUpdates();
    }
}
