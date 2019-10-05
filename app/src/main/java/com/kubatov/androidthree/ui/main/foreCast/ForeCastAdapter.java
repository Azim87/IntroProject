package com.kubatov.androidthree.ui.main.foreCast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kubatov.androidthree.R;
import com.kubatov.androidthree.data.model.forecast_model.Forecast;
import com.kubatov.androidthree.util.DateUtil;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForeCastAdapter extends RecyclerView.Adapter<ForeCastAdapter.ForeCastViewHolder> {
    private List<Forecast> forecastList;
    private Context mContext;

    ForeCastAdapter(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ForeCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForeCastViewHolder(rootView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ForeCastViewHolder holder, int position) {
        Forecast forecast = forecastList.get(position);

        String icon = forecast.list.get(0).weather.get(0).getIcon();
        String IMAGE_URL = "http://openweathermap.org/img/w/" + icon + ".png";

        Glide.with(holder.itemView.getContext())
                .load(IMAGE_URL)
                .apply(new RequestOptions().override(100, 100))
                .into(holder.foreCastIV);

        holder.tempTextView.setText(
                DateUtil.convertUnixToDate(forecast.getList().get(0).dt) + "\n" +
                        forecast.getList().get(0).main.getTemp() + "/" +
                        forecast.getList().get(0).main.getTempMin() + "/" +
                        forecast.getList().get(0).main.getTempMax() + "\n" +
                        forecast.getList().get(0).main.getPressure() + "\n" +
                        forecast.getList().get(0).main.getHumidity() + "\n" +
                        forecast.getList().get(0).weather.get(0).getDescription() + "\n" +
                        DateUtil.convertUnixToHour(forecast.getList().get(0).dt_txt));
    }

    @Override
    public int getItemCount() {
        if (forecastList != null){
            return forecastList.size();
        }else {
            return 0;
        }
    }

    class ForeCastViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.forecast_city_title)
        TextView tempTextView;

        @BindView(R.id.forecast_image)
        ImageView foreCastIV;

        private ForeCastViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


