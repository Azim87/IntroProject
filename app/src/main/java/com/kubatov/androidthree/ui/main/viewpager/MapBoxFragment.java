package com.kubatov.androidthree.ui.main.viewpager;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kubatov.androidthree.R;
import com.kubatov.androidthree.ui.base.BaseFragment;
import com.kubatov.androidthree.ui.main.MainActivity;
import com.kubatov.androidthree.ui.receiver.NotificationReceiver;
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
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.kubatov.androidthree.BuildConfig.MAPBOX_API_KEY;
import static com.kubatov.androidthree.Constants.CHANNEL_1;
import static com.kubatov.androidthree.Constants.MAKI_ICON_CAR;
import static com.kubatov.androidthree.Constants.MAP_BOX;
import static com.kubatov.androidthree.Constants.MESSAGE;
import static com.kubatov.androidthree.Constants.MESSAGES;
import static com.kubatov.androidthree.Constants.TITLE;

public class MapBoxFragment extends BaseFragment implements View.OnClickListener {


    //region Views and Objects
    private final Random random = new Random();
    private MapboxMap mapbox;
    private LocationComponent locationComponent;
    PermissionsManager permissionsManager;
    private SymbolManager symbolManager;
    private NotificationManagerCompat managerCompat;

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
        managerCompat = NotificationManagerCompat.from(getContext());
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
    private void getNotification() {
        managerCompat = NotificationManagerCompat.from(getContext());

        Intent notificationIntent = new Intent(getContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent, 0);

        Intent broadcastIntent = new Intent(getContext(), NotificationReceiver.class);
        broadcastIntent.setAction(MESSAGE);
        broadcastIntent.putExtra("message", MESSAGE);
        PendingIntent actionIntent = PendingIntent.getBroadcast(getContext(), 0, broadcastIntent, PendingIntent.FLAG_ONE_SHOT);


        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_1)
                .setSmallIcon(R.drawable.ic_my_location)
                .setContentTitle(MAP_BOX)
                .setContentText(MESSAGE)
                .setPriority(Notification.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_NAVIGATION)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_my_location, MESSAGES, actionIntent)
                .build();
        managerCompat.notify(1, notification);

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
                Toaster.shortMessage(symbol.getTextField() + " " + symbol.getLatLng()));
    }

    private void setCustomIcon(Style style){
        style.addImageAsync(MAKI_ICON_CAR, BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.loc)));
    }

    private void getRandomMarkers() {
        List<SymbolOptions> symbolOptionsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            symbolOptionsList.add(new SymbolOptions()
                    .withLatLng(createRandomLatLng())
                    .withIconImage(MAKI_ICON_CAR)
                    .withTextField(TITLE)
                    .withTextAnchor("top")
                    .withTextRadialOffset(1F)
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
                .newCameraPosition(CameraPosition.DEFAULT), 7000);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_image_button:
                getNotification();
                currentLocationEnabled();
                return;

            case R.id.stop_image_button:
                return;

            default:
        }

    }
    //endregion
}