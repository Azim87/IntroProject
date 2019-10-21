package com.kubatov.androidthree.ui.main.viewpager.mapbox;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.kubatov.androidthree.R;
import com.kubatov.androidthree.ui.service.TrackingService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapBoxFragment extends BaseMapBoxFragment implements View.OnClickListener {

    @BindView(R.id.start_image_button) ImageButton startImageButton;
    @BindView(R.id.stop_image_button) ImageButton stopImageButton;

    @Override
    protected int getLayoutId() {
        return R.layout.item_map;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        startImageButton.setOnClickListener(this);
        stopImageButton.setOnClickListener(this);
    }

    private void startService() {
        ContextCompat.startForegroundService(getContext(), new Intent(getContext(), TrackingService.class));
    }

    private void stopService(){
        getContext().stopService(new Intent(getContext(), TrackingService.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_image_button:
                startService();
                break;
            case R.id.stop_image_button:
                stopService();
                break;
            default:
                break;
        }
    }
}