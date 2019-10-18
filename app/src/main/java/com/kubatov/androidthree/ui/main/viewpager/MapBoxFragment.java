package com.kubatov.androidthree.ui.main.viewpager;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.kubatov.androidthree.R;
import com.kubatov.androidthree.data.database.NotificationDatabase;
import com.kubatov.androidthree.data.model.notification.Notification;
import com.kubatov.androidthree.ui.base.BaseFragment;
import com.kubatov.androidthree.ui.service.TrackingService;
import com.kubatov.androidthree.util.Toaster;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kubatov.androidthree.BuildConfig.MAPBOX_API_KEY;
import static com.kubatov.androidthree.Constants.DESCRIPTION;
import static com.kubatov.androidthree.Constants.MAKI_ICON_CAR;
import static com.kubatov.androidthree.Constants.TITLE;
import static com.mapbox.mapboxsdk.utils.BitmapUtils.getBitmapFromDrawable;

public class MapBoxFragment extends BaseFragment implements View.OnClickListener {


    //region Views and Objects
    private final Random random = new Random();
    private MapboxMap mapbox;
    private LocationComponent locationComponent;
    private PermissionsManager permissionsManager;
    private SymbolManager symbolManager;
    private FusedLocationProviderClient mLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;

    private double lat;
    private double longit;

    private ArrayList<Notification> notificationArrayList;

    @BindView(R.id.start_image_button)
    ImageButton startImageButton;

    @BindView(R.id.stop_image_button)
    ImageButton stopImageButton;

    @BindView(R.id.mapview)
    MapView mapboxView;
    //endregion

    //region Fragment Methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        Mapbox.getInstance(getContext(), MAPBOX_API_KEY);
        super.onCreate(savedInstanceState);

        notificationArrayList = new ArrayList<>();
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                longit = location.getLongitude();
                lat = location.getLatitude();
                Log.d("ololo", "notification:  " + location.getLatitude() + " " + location.getLongitude());
                Log.d("ololo", "notificationList:  " +lat + " " + longit);

                notificationArrayList.add(
                        new Notification(longit, lat));

                NotificationDatabase.getInstance(getContext()).notificationDao().insert(notificationArrayList);


            }
        };
        initLocationRequest();
        getPermission();

    }

    private void getPermission(){
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Log.d("ololo", "onPermissionGranted: " + response.getPermissionName());
                        requestLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Log.d("ololo", "onPermissionDenied: " + response.isPermanentlyDenied());
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        Log.d("ololo", "onPermissionRationaleShouldBeShown: " + permission.getName());
                    }
                }).check();
    }

    private void requestLocationUpdates(){
        mLocationProviderClient.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.getMainLooper());
    }

    private void initLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000L);
        mLocationRequest.setFastestInterval(5000L);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_map;
    }

    @Override
    protected void initView(View view) {

        ButterKnife.bind(this, view);
        initMap();
        startImageButton.setOnClickListener(this);
    }
    //endregion

    //region MapBoxLifeCycle
    @Override
    public void onStart() {
        super.onStart();
        mapboxView.onStart();
        if (locationComponent != null && getActivity() != null) {
            locationComponent.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapboxView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapboxView.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
        mapboxView.onStop();
        if (locationComponent != null) {
            locationComponent.onStop();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapboxView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapboxView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapboxView.onDestroy();
    }

    //endregion

    //region Notification
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void startService() {
        String title = "Hello, World!";
        String description = "Hello, World 2!";

        Intent titleIntent = new Intent(getContext(), TrackingService.class);
        titleIntent.putExtra(TITLE, title);
        titleIntent.putExtra(DESCRIPTION, description);
        ContextCompat.startForegroundService(getContext(), titleIntent);

    }


    private void stopService(){
        Intent stopIntent = new Intent(getContext(), TrackingService.class);

    }
    //endregion

    //region Init Map
    private void initMap() {

        mapboxView.getMapAsync(mapboxMap -> {
            mapbox = mapboxMap;
            customMarkers(mapboxMap);
        });
    }
    //endregion

    //region Custom Markers
    private void customMarkers(MapboxMap mapboxMap){
        mapboxMap.setStyle(Style.DARK, style -> {
            setCustomIcon(style);
            initSymbolManager(style);
            getRandomMarkers();
        });
    }
    private void initSymbolManager(Style style){
        symbolManager = new SymbolManager(mapboxView, mapbox, style);
        symbolManager.addClickListener(symbol ->
                Toaster.shortMessage( " " + symbol.getLatLng()));
    }

    private void setCustomIcon(Style style){
        style.addImageAsync(MAKI_ICON_CAR, Objects.requireNonNull(getBitmapFromDrawable(getResources().getDrawable(R.drawable.ic_custom_marker))));
    }

    private void getRandomMarkers() {
        mapbox.setCameraPosition(new CameraPosition.Builder()
                .target(createRandomLatLng())
                .zoom(10)
                .tilt(50)
                .bearing(180)
                .build());
        mapbox.animateCamera(CameraUpdateFactory
                .newCameraPosition(CameraPosition.DEFAULT), 10000);

        List<SymbolOptions> symbolOptionsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            symbolOptionsList.add(new SymbolOptions()
                    .withLatLng(createRandomLatLng())
                    .withIconImage(MAKI_ICON_CAR)
                    .withDraggable(true));
        }
        symbolManager.create(symbolOptionsList);
        symbolManager.setIconAllowOverlap(true);
        symbolManager.setIconTranslate(new Float[]{-4f, 7f});
    }

    private LatLng createRandomLatLng() {
        return new LatLng(
                (random.nextDouble() * -42.0) + 79.1,
                (random.nextDouble() * -41.0) + 79.1);
    }
    //endregion

    //region Current Location
    private void cameraPosition(MapboxMap mapboxMap) {
        mapboxMap.setCameraPosition(new CameraPosition.Builder().zoom(14)
                .bearing(180)
                .tilt(30)
                .build());
        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(CameraPosition.DEFAULT), 10000);
    }

    private void currentLocationEnabled(){
        mapbox.setStyle(Style.DARK, style -> MapBoxFragment.this.locationEnable(style));
        cameraPosition(mapbox);
    }

    @SuppressWarnings({"MissingPermission"})
    private void locationEnable(Style style) {
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {
            locationComponent = mapbox.getLocationComponent();
            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(getContext(), style).build());
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }
    //endregion

    //region On Click
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_image_button:
                startService();
                currentLocationEnabled();
                return;

            case R.id.stop_image_button:
                stopService();
                return;

            default:
        }

    }
    //endregion
}