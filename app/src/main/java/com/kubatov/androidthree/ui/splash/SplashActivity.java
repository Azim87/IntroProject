package com.kubatov.androidthree.ui.splash;

import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kubatov.androidthree.App;
import com.kubatov.androidthree.ui.main.viewpager.CurrentWeatherFragment;
import com.kubatov.androidthree.ui.onboard.OnBoardActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       new CountDownTimer(1000,500){

           @Override
           public void onTick(long millisUntilFinished) {
           }

           @Override
           public void onFinish() {
               checkIsFirstLaunch();
           }
       }.start();
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
