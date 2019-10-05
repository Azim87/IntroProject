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
        String icon = forecastList.get(position).getList().get(position).weather.get(0).getIcon();
        String IMAGE_URL = "http://openweathermap.org/img/w/" + icon + ".png";

        Glide.with(holder.itemView.getContext())
                .load(IMAGE_URL)
                .apply(new RequestOptions().override(100, 100))
                .into(holder.foreCastIV);

        holder.tempTextView.setText(
                DateUtil.convertUnixToDate(
                        forecastList.get(position).getList().get(position).dt) + "\n" +
                        forecastList.get(position).getList().get(position).main.getTemp() + "/" +
                        forecastList.get(position).getList().get(position).main.getTempMin() + "/" +
                        forecastList.get(position).getList().get(position).main.getTempMax() + "\n" +
                        forecastList.get(position).getList().get(position).main.getPressure() + "\n" +
                        forecastList.get(position).getList().get(position).main.getHumidity() + "\n" +
                        forecastList.get(position).getList().get(position).weather.get(position).getDescription() + "\n" +
                DateUtil.convertUnixToHour(
                        forecastList.get(position).getList().get(position).dt_txt));
    }

    @Override
    public int getItemCount() {
        if (forecastList != null) {
            return forecastList.size();
        } else {
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


