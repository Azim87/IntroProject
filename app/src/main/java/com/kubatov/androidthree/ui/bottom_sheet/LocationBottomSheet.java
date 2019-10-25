package com.kubatov.androidthree.ui.bottom_sheet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kubatov.androidthree.App;
import com.kubatov.androidthree.R;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationBottomSheet extends BottomSheetDialogFragment{
    private DecimalFormat dfFormat = new DecimalFormat("#,##0");

    @BindView(R.id.bottom_sheet_distance_text) TextView distanceTextView;
    @BindView(R.id.bottom_sheet_duration_text) TextView durationTextView;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_sheet_location, container, false);
        ButterKnife.bind(this, rootView);

        double distance = App.getPreferenceHelper().getDistances();
        double duration = App.getPreferenceHelper().getDurations();


        distanceTextView.setText("distance:  "  + dfFormat.format(distance) + "m");
        durationTextView.setText("duration:  "  + dfFormat.format(duration) + "d");

        Log.d("ololo", "onCreateView: " + distance + " " + duration);
        return rootView;
    }

   /* @Override
    public void showDistance(double distance) {
        distanceTextView.setText("distance: "  + distance + "m");
    }

    @Override
    public void showDuration(double duration) {
        durationTextView.setText("duration: "  + duration);
    }*/
}
