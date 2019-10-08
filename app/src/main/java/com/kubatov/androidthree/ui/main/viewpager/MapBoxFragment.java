package com.kubatov.androidthree.ui.main.viewpager;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kubatov.androidthree.R;
import com.kubatov.androidthree.ui.base.BaseFragment;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapBoxFragment extends BaseFragment {

    @BindView(R.id.mapview)
    MapView mapboxView;
    private MapboxMap mapbox;
    private LocationComponent locationComponent;
    private PermissionsManager permissionsManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(getContext(), getString(R.string.access_token_mapbox));
        super.onCreate(savedInstanceState);
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
        initedMap();
    }

    private void initedMap() {
        mapboxView.getMapAsync(mapboxMap -> {
            mapbox = mapboxMap;
            mapboxMap.setStyle(Style.DARK);

            /*mapboxMap.setCameraPosition(new CameraPosition.Builder().zoom(10)
                    .bearing(180)
                    .tilt(30)
                    .build());
            mapboxMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(CameraPosition.DEFAULT), 7000);*/

            //camera position bishkek
            mapboxMap.addMarker(new MarkerOptions().setPosition(new LatLng(42.8747057, 74.6101724, 14.92)).setTitle("Bishkek"));
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(42.8747057, 74.6101724, 14.92)) // Sets the new camera position
                    .zoom(10) // Sets the zoom
                    .bearing(180) // Rotate the camera
                    .tilt(30) // Set the camera tilt
                    .build(); // Creates a CameraPosition from the builder
            mapboxMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), 7000);

            /*mapboxMap.setStyle(Style.DARK, style ->
                    MapBoxFragment.this.locationEnable(style));*/
        });
    }

    //@SuppressWarnings({"MissingPermission"})
    /*void locationEnable(Style style) {
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {
            locationComponent = mapbox.getLocationComponent();
            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(getContext(), style).build());
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }*/
}

