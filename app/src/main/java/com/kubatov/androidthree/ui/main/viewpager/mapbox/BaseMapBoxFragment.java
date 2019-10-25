package com.kubatov.androidthree.ui.main.viewpager.mapbox;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kubatov.androidthree.R;
import com.kubatov.androidthree.ui.base.BaseFragment;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;

import butterknife.BindView;

import static com.kubatov.androidthree.BuildConfig.MAPBOX_API_KEY;

abstract class BaseMapBoxFragment extends BaseFragment {

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