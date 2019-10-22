package com.kubatov.androidthree.ui.main.viewpager.mapbox;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.kubatov.androidthree.R;
import com.kubatov.androidthree.ui.base.BaseFragment;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import butterknife.BindView;

import static com.kubatov.androidthree.BuildConfig.MAPBOX_API_KEY;

abstract class BaseMapBoxFragment extends BaseFragment {

    private MapboxMap mapbox;
    private LocationComponent locationComponent;
    private PermissionsManager permissionsManager;

    @BindView(R.id.mapview)
    MapView mapBoxView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(getContext(), MAPBOX_API_KEY);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        askForPermission();
        initMap();
    }

    private void askForPermission(){
        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                Log.d("ololo", "onPermissionGranted: ");
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Log.d("ololo", "onPermissionDenied: ");
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                Log.d("ololo", "onPermissionRationaleShouldBeShown: ");
            }
        }).check();
    }

    private void initMap() {
        mapBoxView.getMapAsync(mapboxMap -> {
            mapbox = mapboxMap;
            currentLocationEnabled();
        });
    }

    private void cameraPosition(MapboxMap mapboxMap) {
        mapboxMap.setCameraPosition(new CameraPosition.Builder().zoom(14)
                .bearing(180)
                .tilt(30)
                .build());
        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(CameraPosition.DEFAULT), 10000);
    }

    private void currentLocationEnabled(){
        mapbox.setStyle(Style.DARK, style -> BaseMapBoxFragment.this.locationEnable(style));
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

    @Override
    public void onStart() {
        super.onStart();
        mapBoxView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapBoxView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapBoxView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapBoxView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapBoxView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapBoxView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapBoxView.onDestroy();
    }
}