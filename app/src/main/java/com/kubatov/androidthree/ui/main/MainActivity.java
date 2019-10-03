package com.kubatov.androidthree.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kubatov.androidthree.R;
import com.kubatov.androidthree.data.model.current_weather.CurrentWeather;
import com.kubatov.androidthree.data.network.RetroFitBuilder;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private int position = 0;
    //region InitViews
    @BindView(R.id.text_view_city)
    TextView textViewCity;

    @BindView(R.id.text_view_temp)
    TextView textViewTemp;

    @BindView(R.id.text_view_description)
    TextView textViewDescription;

    @BindView(R.id.text_view_humidity)
    TextView textViewHumidity;

    @BindView(R.id.progress_load_bar)
    ProgressBar weatherProgress;
    //endregion

   public static void start(Context context){
       context.startActivity(new Intent(context, MainActivity.class));
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getCurrentWeather();
    }

    //region CurrentWeather
    private void getCurrentWeather(){
       String city = "Bishkek";
       RetroFitBuilder.getService().getWeatherByName(
               city,
               getResources().getString(R.string.api_key),
               "metric")
               .enqueue(new Callback<CurrentWeather>() {
                   @SuppressLint("SetTextI18n")
                   @Override
                   public void onResponse(@Nullable Call<CurrentWeather> call, @Nullable Response<CurrentWeather> response) {
                       assert response.body() != null;
                       textViewCity.setText("Weather in " + response.body().getName() + ", " + "KG");
                       textViewTemp.setText( response.body().getMain().getTemp().intValue() + " °C");
                       textViewHumidity.setText("Humidity: " + response.body().getMain().getHumidity().toString() + " %");
                       textViewDescription.setText(response.body().getWeather().get(position).getDescription());
                   }
                   @Override
                   public void onFailure(@Nullable Call<CurrentWeather> call, Throwable t) {
                   }
               });

    }
    //endregion
    //region RefreshWeather
    public void onRefreshClick(View view) {
       showProgressBar();
        String city = "Bishkek";
        RetroFitBuilder.getService().getWeatherByName(
                city,
                getResources().getString(R.string.api_key),
                "metric")
                .enqueue(new Callback<CurrentWeather>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@Nullable Call<CurrentWeather> call, @Nullable Response<CurrentWeather> response) {
                        if (response != null) {
                            hideProgressBar();
                            Toast.makeText(MainActivity.this, "updated", Toast.LENGTH_SHORT).show();
                            assert response.body() != null;
                            textViewCity.setText("Weather in " + response.body().getName() + ", " + "KG");
                            textViewTemp.setText(response.body().getMain().getTemp().intValue() + " °C");
                            textViewHumidity.setText("Humidity: " + response.body().getMain().getHumidity().toString() + " %");
                            textViewDescription.setText(response.body().getWeather().get(position).getDescription());
                        }
                    }
                    @Override
                    public void onFailure(@Nullable Call<CurrentWeather> call, Throwable t) {
                    }
                });
    }
    //endregion
    //region ProgressBar
    private void showProgressBar(){
        weatherProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        weatherProgress.setVisibility(View.GONE);
    }
    //endregion
}