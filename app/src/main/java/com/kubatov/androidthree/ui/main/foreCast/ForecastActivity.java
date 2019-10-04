package com.kubatov.androidthree.ui.main.foreCast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.kubatov.androidthree.R;
import com.kubatov.androidthree.data.model.forecast_model.Forecast;
import com.kubatov.androidthree.data.network.RetroFitBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastActivity extends AppCompatActivity {

    private ForeCastAdapter adapter;
    /*Forecast forecast;*/
    ArrayList<Forecast> list;

    @BindView(R.id.forecast_recycler_view)
    RecyclerView foreCastRecycler;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ForecastActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        ButterKnife.bind(this);

        list = new ArrayList<>();
        initRecycler();
    }

    private void initRecycler() {
        foreCastRecycler.setLayoutManager(new LinearLayoutManager(this));
        foreCastRecycler.setHasFixedSize(true);
        adapter = new ForeCastAdapter(list);
        foreCastRecycler.setAdapter(adapter);


        getForeCastData();
    }

    private void getForeCastData() {
        RetroFitBuilder.getService().getForecast(
                "Bishkek",
                getResources().getString(R.string.api_key),
                "metric"

        ).enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(@Nullable Call<Forecast> call, @Nullable Response<Forecast> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d("ololo", "body " + response.body().list.get(0).dt_txt);

                        list.add(new Forecast(
                                response.body().getCod(),
                                response.body().getMessage(),
                                response.body().getCity(),
                                response.body().getCnt(),
                                response.body().getList()));
                        adapter.notifyDataSetChanged();

                        Log.d("ololo", "body " + response.body().getCod());
                        Log.d("ololo", "body " + response.body().getMessage());
                        Log.d("ololo", "body " + response.body().getCity().getName());
                        Log.d("ololo", "body " + response.body().getCnt());
                        Log.d("ololo", "body " + response.body().getList().get(0).main.getTempMax().intValue());





                    } else {
                        Toast.makeText(ForecastActivity.this, "The body is empty", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ForecastActivity.this, "Request error" + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("ololo", "code " + response.code());
                }


            }

            @Override
            public void onFailure(@Nullable Call<Forecast> call, @Nullable Throwable t) {
                Log.d("ololo", "failure " + t.getMessage());


            }
        });
    }

}