package com.kubatov.androidthree.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kubatov.androidthree.R;
import com.kubatov.androidthree.data.model.current_weather.CurrentWeather;
import com.kubatov.androidthree.data.network.RetroFitBuilder;
import com.kubatov.androidthree.ui.main.foreCast.ForecastActivity;
import com.kubatov.androidthree.util.Toaster;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String CITY = "Bishkek";
    public static final String METRIC = "metric";
    private int position = 0;
    private String country;

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

    @BindView(R.id.weather_image)
    ImageView weatherImageView;

    @BindView(R.id.edit_text_country)
    EditText editCountry;



    //endregion
   public static void start(Context context){
       context.startActivity(new Intent(context, MainActivity.class));
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        editCountryName();
    }

    //region CurrentWeather
    private void getCurrentWeather(){
       showProgressBar();


       RetroFitBuilder.getService().getWeatherByName(

               country, getResources().getString(R.string.api_key),METRIC)
               .enqueue(new Callback<CurrentWeather>() {
                   @SuppressLint("SetTextI18n")
                   @Override
                   public void onResponse(@Nullable Call<CurrentWeather> call, @Nullable Response<CurrentWeather> response) {

                       if (response.isSuccessful()){
                           if (response.body() != null){
                               hideProgressBar();
                               String icon = response.body().getWeather().get(0).getIcon();
                               String IMAGE_URL = "http://openweathermap.org/img/w/" + icon + ".png";

                               Glide.with(MainActivity.this).load(IMAGE_URL).apply(new RequestOptions().override(200, 200)).into(weatherImageView);

                                       textViewCity.setText("Weathers in " + response.body().getName() + ", " + "KG");
                                       textViewTemp.setText( response.body().getMain().getTemp().intValue() + "°C");
                                       textViewHumidity.setText(response.body().getMain().getHumidity().toString() + " %");
                                       textViewDescription.setText(response.body().getWeather().get(position).getMain());

                           }else {
                               Toaster.longMessage("The body is empty" + response.body());}

                       }else {
                           Toaster.longMessage("Request error" + response.code());}
                   }
                   @Override
                   public void onFailure(@Nullable Call<CurrentWeather> call, Throwable t) {
                       Toaster.longMessage("Error" + t.getMessage());
                   }
               });
    }
    //endregion

    //region RefreshWeather
    public void onRefreshClick(View view) {
       getCurrentWeather();
       Toaster.shortMessage("Updated");
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

    private void onForecastClick(View view) {
       ForecastActivity.start(this);
    }

    private void editCountryName(){

       editCountry.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
           }

           @Override
           public void afterTextChanged(Editable s) {
               country = s.toString();
           }
       });
    }


}
