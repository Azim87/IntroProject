package com.kubatov.androidthree.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.kubatov.androidthree.R;
import com.kubatov.androidthree.ui.main.viewpager.CurrentWeatherAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    CurrentWeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        weatherAdapter = new CurrentWeatherAdapter(getSupportFragmentManager());
        viewPager.setAdapter(weatherAdapter);
    }
}
