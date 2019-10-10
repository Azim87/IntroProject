package com.kubatov.androidthree.ui.main.viewpager;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kubatov.androidthree.R;
import com.kubatov.androidthree.ui.base.BaseFragment;
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
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kubatov.androidthree.App.CHANNEL_1;
import static com.kubatov.androidthree.BuildConfig.MAPBOX_API_KEY;

public class MapBoxFragment extends BaseFragment {

    private static final String MAKI_ICON_CAR = "car-15";
    private final Random random = new Random();

    private MapboxMap mapbox;
    private LocationComponent locationComponent;
    private PermissionsManager permissionsManager;
    private SymbolManager symbolManager;
    @BindView(R.id.image_button)
    ImageButton imageButton;

    @BindView(R.id.mapview)
    MapView mapboxView;
    private NotificationManagerCompat managerCompat;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(getContext(), MAPBOX_API_KEY);
        super.onCreate(savedInstanceState);
        managerCompat = NotificationManagerCompat.from(getContext());
    }

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

    @Override
    protected int getLayoutId() {
        return R.layout.item_map;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        initMap();
    }

    private void getNotification() {
        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_1)
                .setSmallIcon(R.drawable.ic_my_location)
                .setContentTitle("MAP BOX")
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentText("Getting your location...")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        managerCompat.notify(1, notification);
    }

    private void initMap() {

        mapboxView.getMapAsync(mapboxMap -> {
            mapbox = mapboxMap;

            imageButton.setOnClickListener(v -> {
                getNotification();
                cameraPosition(mapboxMap);
                mapboxMap.setStyle(Style.DARK, style -> MapBoxFragment.this.locationEnable(style));
            });

            mapboxMap.setStyle(Style.DARK, style -> {
                mapboxMap.moveCamera(CameraUpdateFactory.zoomTo(2));

                symbolManager = new SymbolManager(mapboxView, mapboxMap, style);
                symbolManager.addClickListener(symbol ->
                        Toaster.shortMessage(symbol.getTextField() + " " + symbol.getLatLng()));
                getRandomMarkers();
            });
        });
    }

    private void cameraPosition(MapboxMap mapboxMap) {
        mapboxMap.setCameraPosition(new CameraPosition.Builder().zoom(14)
                .bearing(180)
                .tilt(30)
                .build());
        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(CameraPosition.DEFAULT), 7000);
    }

    private void getRandomMarkers() {
        List<SymbolOptions> symbolOptionsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            symbolOptionsList.add(new SymbolOptions()
                    .withLatLng(createRandomLatLng())
                    .withIconImage(MAKI_ICON_CAR)
                    .withTextField("OLOLO")
                    .withTextAnchor("top")
                    .withTextRadialOffset(1F)
                    .withDraggable(false));
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
}