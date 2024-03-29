package com.kubatov.androidthree.ui.main.forecast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

import static com.kubatov.androidthree.ui.main.viewpager.CurrentWeatherFragment.CITY;
import static com.kubatov.androidthree.ui.main.viewpager.CurrentWeatherFragment.METRIC;

public class ForecastActivity extends AppCompatActivity {

    private ForeCastAdapter adapter;
    private List<Mylist> forecastsList;

    @BindView(R.id.forecast_recycler_view)
    RecyclerView foreCastRecycler;

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

    private void initRecycler() {
        foreCastRecycler.setLayoutManager(new LinearLayoutManager(this));
        foreCastRecycler.setHasFixedSize(true);
        adapter = new ForeCastAdapter(forecastsList);
        getForeCastData();
    }

    private void getForeCastData() {
        RetroFitBuilder.getService().getForecast(
                CITY,
                getString(R.string.api_key),
                METRIC

        ).enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(@Nullable Call<Forecast> call, @Nullable Response<Forecast> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        for (Mylist mylist : response.body().getList())


                            forecastsList.addAll(response.body().getList());
                        /*forecastsList.add(new Forecast(
                                response.body().getCod(),
                                response.body().getMessage(),
                                response.body().getCity(),
                                response.body().getCnt(),
                                response.body().getList()));*/
                        foreCastRecycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } else {
                        Toaster.shortMessage( "The body is empty");
                    }
                } else {
                    Toaster.shortMessage("Request error"); } }

            @Override
            public void onFailure(@Nullable Call<Forecast> call, @Nullable Throwable t) {
                Toaster.shortMessage("fail" + t.getMessage());
            }
        });
    }
}