package com.kubatov.androidthree.ui.splash;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kubatov.androidthree.App;
import com.kubatov.androidthree.ui.main.viewpager.CurrentWeatherFragment;
import com.kubatov.androidthree.ui.onboard.OnBoardActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIsFirstLaunch();
    }

    private void checkIsFirstLaunch() {
        if (App.getPreferenceHelper().isFirstLaunch()) {
            App.getPreferenceHelper().setFirstLaunch();
            OnBoardActivity.start(this);
            finish();

        } else
            CurrentWeatherFragment.start(this);
            finish();
        }
    }
