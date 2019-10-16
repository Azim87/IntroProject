package com.kubatov.androidthree.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.kubatov.androidthree.R;
import com.kubatov.androidthree.ui.main.viewpager.CurrentWeatherAdapter;
import com.kubatov.androidthree.ui.main.viewpager.CurrentWeatherFragment;
import com.kubatov.androidthree.ui.main.viewpager.MapBoxFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private CurrentWeatherAdapter weatherAdapter;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        weatherAdapter = new CurrentWeatherAdapter(getSupportFragmentManager());
        weatherAdapter.setAdapter(getFragment());
        viewPager.setAdapter(weatherAdapter);
    }

    private List<Fragment> getFragment() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new CurrentWeatherFragment());
        fragmentList.add(new MapBoxFragment());
        return fragmentList;
    }
}
