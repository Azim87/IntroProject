package com.kubatov.androidthree.ui.main.foreCast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.kubatov.androidthree.R;
import com.kubatov.androidthree.data.model.forecast_model.Forecast;
import com.kubatov.androidthree.data.network.RetroFitBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastActivity extends AppCompatActivity {

    @BindView(R.id.forecast_recycler_view)
    RecyclerView foreCastRecycler;

    public static void start(Context context){
        context.startActivity(new Intent(context, ForecastActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        ButterKnife.bind(this);

        initRecycler();
    }
    private  void initRecycler(){
        foreCastRecycler.setLayoutManager(new LinearLayoutManager(this));
        foreCastRecycler.setHasFixedSize(true);
        foreCastRecycler.setAdapter(new ForeCastAdapter());

        getForeCastData();
    }

    private void getForeCastData(){
        RetroFitBuilder.getService().getForecast("Bishkek", 4,
                getResources().getString(R.string.api_key), "metric")
                .enqueue(new Callback<List<Forecast>>() {
                    @Override
                    public void onResponse(Call<List<Forecast>> call, Response<List<Forecast>> response) {

                    }

                    @Override
                    public void onFailure(Call<List<Forecast>> call, Throwable t) {
                        Log.d("ololo", "on failure " + t.getMessage());
                    }
                });
    }
}
