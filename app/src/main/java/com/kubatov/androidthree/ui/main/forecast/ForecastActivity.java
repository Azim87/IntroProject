package com.kubatov.androidthree.ui.main.forecast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kubatov.androidthree.R;
import com.kubatov.androidthree.data.model.forecast_model.Forecast;
import com.kubatov.androidthree.data.model.forecast_model.Mylist;
import com.kubatov.androidthree.data.network.RetroFitBuilder;
import com.kubatov.androidthree.util.Toaster;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kubatov.androidthree.BuildConfig.WEATHER_API_KEY;
import static com.kubatov.androidthree.Constants.LANG;
import static com.kubatov.androidthree.Constants.METRIC;

public class ForecastActivity extends AppCompatActivity {

    private ForeCastAdapter adapter;
    private List<Mylist> forecastsList;

    @BindView(R.id.forecast_recycler_view)
    RecyclerView foreCastRecycler;

    @BindView(R.id.forcast_et)
    EditText forcastEditT;

    String forcastCity;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ForecastActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        forecastsList = new ArrayList<>();
        ButterKnife.bind(this);
        initRecycler();

    }
    private void getForCastTitle(){
        forcastCity = forcastEditT.getText().toString().trim();
    }

    private void initRecycler() {
        foreCastRecycler.setLayoutManager(new LinearLayoutManager(this));
        foreCastRecycler.setHasFixedSize(true);
        adapter = new ForeCastAdapter(forecastsList);
    }

    private void getForeCastData() {
        getForCastTitle();
        RetroFitBuilder.getService().getForecast(
                forcastCity,
                LANG,
                WEATHER_API_KEY,
                METRIC

        ).enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(@Nullable Call<Forecast> call, @Nullable Response<Forecast> response) {
                setData(response);
            }

            @Override
            public void onFailure(@Nullable Call<Forecast> call, @Nullable Throwable t) {
                Toaster.shortMessage("No internet connection!");
            }
        });
    }

    private void setData(Response<Forecast> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                forecastsList.addAll(response.body().getList());
                foreCastRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                Toaster.shortMessage("The body is empty");
            }
        } else {
            Toaster.shortMessage("Request error");
        }
    }

    public void onSearchClick(View view) {
        getForeCastData();
    }
}