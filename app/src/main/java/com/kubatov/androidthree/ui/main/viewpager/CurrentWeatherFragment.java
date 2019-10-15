package com.kubatov.androidthree.ui.main.viewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kubatov.androidthree.App;
import com.kubatov.androidthree.R;
import com.kubatov.androidthree.data.model.current_weather.CurrentWeather;
import com.kubatov.androidthree.data.network.RetroFitBuilder;
import com.kubatov.androidthree.ui.base.BaseFragment;
import com.kubatov.androidthree.ui.main.MainActivity;
import com.kubatov.androidthree.ui.main.forecast.ForecastActivity;
import com.kubatov.androidthree.util.Toaster;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kubatov.androidthree.BuildConfig.WEATHER_API_KEY;
import static com.kubatov.androidthree.Constants.ICON_URL;
import static com.kubatov.androidthree.Constants.METRIC;

public class CurrentWeatherFragment extends BaseFragment {

    //region InitViews
    @BindView(R.id.text_view_city)
    TextView textViewCity;
    @BindView(R.id.forecast_weather)
    TextView textViewForecast;
    @BindView(R.id.text_view_temp)
    TextView textViewTemp;
    @BindView(R.id.text_view_description)
    TextView textViewDescription;
    @BindView(R.id.text_view_humidity)
    TextView textViewHumidity;
    @BindView(R.id.humidity)
    TextView texHumidity;
    @BindView(R.id.progress_load_bar)
    ProgressBar weatherProgress;
    @BindView(R.id.weather_image)
    ImageView weatherImageView;
    @BindView(R.id.edit_text_country)
    EditText editCountry;
    @BindView(R.id.refresh_button)
    Button button;
    private int position = 0;
    private String country;
    //endregion

    public static void start(Context context) {
        context.startActivity(new Intent(App.context, MainActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_current_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        editCountryName();

        textViewForecast.setOnClickListener(v1 -> {
            ForecastActivity.start(this.getActivity());
        });

        button.setOnClickListener(v -> {
            getCurrentWeather();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getCurrentWeather() {
        showProgressBar();

        RetroFitBuilder.getService().getWeatherByName(
                country, WEATHER_API_KEY, METRIC)

                .enqueue(new Callback<CurrentWeather>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@Nullable Call<CurrentWeather> call, @Nullable Response<CurrentWeather> response) {
                        validationEditCountry();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                hideProgressBar();
                                showViews(View.VISIBLE);
                                setData(response);
                            } else {
                                Toaster.longMessage("The body is empty" + response.body());
                            }
                        } else {
                            Toaster.longMessage("Request Error");
                        }
                    }

                    @Override
                    public void onFailure(@Nullable Call<CurrentWeather> call, Throwable t) {
                        Toaster.longMessage("Error" + t.getMessage());
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void setData(Response<CurrentWeather> response) {
        String icon = response.body().getWeather().get(0).getIcon();
        String IMAGE_URL = ICON_URL + icon + ".png";
        Glide.with(Objects.requireNonNull(getContext())).load(IMAGE_URL).apply(new RequestOptions().override(200, 200)).into(weatherImageView);
        textViewCity.setText("Weathers in " + response.body().getName() + ", " + response.body().getSys().getCountry());
        textViewTemp.setText(response.body().getMain().getTemp().intValue() + "°C");
        textViewHumidity.setText(response.body().getMain().getHumidity().toString() + " %");
        textViewDescription.setText(response.body().getWeather().get(position).getDescription());
    }

    private void showProgressBar() {
        weatherProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        weatherProgress.setVisibility(View.GONE);
    }

    private void editCountryName() {
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

    private void showViews(int visibility) {
        textViewCity.setVisibility(visibility);
        textViewTemp.setVisibility(visibility);
        textViewHumidity.setVisibility(visibility);
        texHumidity.setVisibility(visibility);
        textViewDescription.setVisibility(visibility);
    }

    private void validationEditCountry() {
        if (editCountry.getText().toString().isEmpty()) {
            editCountry.setError("Insert country");
        } else {
            Toaster.shortMessage("city: " + country);
        }
    }
}
