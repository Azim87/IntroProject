package com.kubatov.androidthree.ui.main.viewpager.mapbox;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.kubatov.androidthree.App;
import com.kubatov.androidthree.R;
import com.kubatov.androidthree.ui.bottom_sheet.LocationBottomSheet;
import com.kubatov.androidthree.ui.service.TrackingService;
import com.kubatov.androidthree.util.Toaster;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.mapboxsdk.utils.ColorUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kubatov.androidthree.BuildConfig.MAPBOX_API_KEY;
import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ROTATION_ALIGNMENT_VIEWPORT;

public class MapBoxFragment extends BaseMapBoxFragment implements View.OnClickListener, MapboxMap.OnMapClickListener {

    private static final String LOCATION_BOTTOM_SHEET = "LocationBottomSheet";
    private static final String ID_ICON_AIRPORT = "airport";

    private MapboxMap mapBox;
    private LocationComponent locationComponent;
    private SymbolManager mSymbolManager;
    private Location mLocation;

    @BindView(R.id.start_image_button)
    ImageButton startImageButton;
    @BindView(R.id.stop_image_button)
    ImageButton stopImageButton;


    //region Fragment Methods
    @Override
    protected int getLayoutId() {
        return R.layout.item_map;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        askForPermission();
        startImageButton.setOnClickListener(this);
        stopImageButton.setOnClickListener(this);
    }
    //endregion

    //region Permissions
    private void askForPermission() {
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Log.d("ololo", "onPermissionGranted: ");
                        response.getRequestedPermission();
                        initMap();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Log.d("ololo", "onPermissionDenied: ");
                        response.getRequestedPermission();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        Log.d("ololo", "onPermissionRationaleShouldBeShown: ");
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toaster.shortMessage("Permission granted! ");
        } else {
            Toaster.shortMessage("Permission denied!");
        }
    }
    //endregion

    //region Init Map
    private void initMap() {
        mapBoxView.getMapAsync(mapboxMap -> {
            mapBox = mapboxMap;

            mapBox.addOnMapClickListener(this);
            cameraPosition(mapboxMap);

            mapboxMap.setStyle(Style.DARK, style -> {
                initSymbolManager(style);
                addCustomMarkerIcon(style);

                mapBox.getStyle().addSource(new GeoJsonSource("myRoute"));
                mapBox.getStyle().addLayer(
                        new LineLayer("direction", "myRoute")
                                .withProperties(
                                        PropertyFactory.lineWidth(5f),
                                        PropertyFactory.lineColor(Color.YELLOW)));
            });
        });
    }
    //endregion

    //region Custom Marker
    private void initSymbolManager(Style style) {
        mSymbolManager = new SymbolManager(mapBoxView, mapBox, style);
        getCustomMarker();
    }

    private void getCustomMarker() {
        SymbolOptions nearbyOptions = new SymbolOptions()
                .withLatLng(new LatLng(42.880493, 74.653400))
                .withIconImage(ID_ICON_AIRPORT)
                .withIconColor(ColorUtils.colorToRgbaString(Color.YELLOW))
                .withIconSize(1.5f)
                .withSymbolSortKey(3.0f)
                .withDraggable(true);
        mSymbolManager.create(nearbyOptions);
        mSymbolManager.setIconAllowOverlap(true);
        mSymbolManager.setIconTranslate(new Float[]{-2f, 3f});
        mSymbolManager.setIconRotationAlignment(ICON_ROTATION_ALIGNMENT_VIEWPORT);
    }

    private void addCustomMarkerIcon(Style style) {
        style.addImage(ID_ICON_AIRPORT, Objects.requireNonNull(
                BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.ic_custom_marker))));
    }
    //endregion

    //region Current Location
    private void currentLocationEnabled() {
        mapBox.setStyle(Style.DARK, style -> MapBoxFragment.this.currentLocationEnable(style));
    }

    private void cameraPosition(MapboxMap mapboxMap) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(42.880493, 74.653400))
                .zoom(13)
                .bearing(180)
                .tilt(30)
                .build();
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 5000);
    }

    private void currentLocationEnable(Style style) {
        locationComponent = mapBox.getLocationComponent();
        locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(getContext(), style).build());
        locationComponent.setLocationComponentEnabled(true);
        locationComponent.setCameraMode(CameraMode.TRACKING);
        locationComponent.setRenderMode(RenderMode.COMPASS);
    }
    //endregion

    //region Foreground Service
    private void startService() {
        ContextCompat.startForegroundService(getContext(), new Intent(getContext(), TrackingService.class));
    }

    private void stopService() {
        getContext().stopService(new Intent(getContext(), TrackingService.class));
    }
    //endregion

    //region OnClick
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_image_button:
                startService();
                currentLocationEnabled();
                break;
            case R.id.stop_image_button:
                stopService();
                break;
            default:
                break;
        }
    }
    //endregion

    //region Track MyRoute
    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        showLocationBottomSheetDialog();
        getMapBoxDirections(point);
        return false;
    }

    private void getMapBoxDirections(LatLng point) {
        MapboxDirections mapboxDirectionsClient = MapboxDirections.builder()
                .origin(Point.fromLngLat(74.653400, 42.880493))
                .destination(Point.fromLngLat(point.getLongitude(), point.getLatitude()))
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken(MAPBOX_API_KEY)
                .build();

        mapboxDirectionsClient.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        trackMyRoute(response.body());
                        saveToShared(response);
                    } else {
                        Toaster.shortMessage("The response is not successful " + response.code());
                    }
                } else {
                    Toaster.shortMessage("The body is null " + response.body());
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                Toaster.longMessage(" " + t.getMessage());
            }
        });
    }


    private void trackMyRoute(DirectionsResponse body) {
        if (!body.routes().isEmpty()) {
            Feature trackMyRouteFeature = Feature.fromGeometry(LineString.fromPolyline(body.routes().get(0).geometry(), PRECISION_6));
            ((GeoJsonSource) (mapBox.getStyle().getSource("myRoute"))).setGeoJson(trackMyRouteFeature);
        }
    }
    //endregion

    //region Save To Shared
    private void saveToShared(Response<DirectionsResponse> response){
        float distance = response.body().routes().get(0).distance().floatValue();
        float duration = response.body().routes().get(0).duration().floatValue();

        App.getPreferenceHelper().setDurations(distance);
        App.getPreferenceHelper().setDistances(duration);

        Log.d("ololo", "on response: " + distance + " " + duration);

        Toaster.longMessage("distance: " + response.body().routes().get(0).distance() + " " +
                "duration: " + response.body().routes().get(0).duration());
    }
    //endregion

    //region Bottom Sheet
    private void showLocationBottomSheetDialog() {
        LocationBottomSheet locationBottomSheet = new LocationBottomSheet();
        if (getFragmentManager() != null) {
            locationBottomSheet.show(getFragmentManager(), LOCATION_BOTTOM_SHEET);
        } else {
            Toaster.longMessage("This bottom sheet view is not found!");
        }
    }
    //endregion
}