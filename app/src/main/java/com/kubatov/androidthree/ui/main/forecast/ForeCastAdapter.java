package com.kubatov.androidthree.ui.main.forecast;

import android.annotation.SuppressLint;
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
import com.kubatov.androidthree.data.model.forecast_model.Mylist;
import com.kubatov.androidthree.util.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kubatov.androidthree.Constants.ICON_URL;

public class ForeCastAdapter extends RecyclerView.Adapter<ForeCastAdapter.ForeCastViewHolder> {
    private List<Mylist> forecastList;

    ForeCastAdapter(List<Mylist> forecastList) {
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
        String icon = forecastList.get(position).weather.get(0).getIcon();
        String IMAGE_URL = ICON_URL + icon + ".png";

        Glide.with(holder.itemView.getContext())
                .load(IMAGE_URL)
                .apply(new RequestOptions().override(100, 100))
                .into(holder.foreCastIV);

        holder.tempTextView.setText(
                DateUtil.convertUnixToDate(forecastList.get(position).dt) + "      " +
                        forecastList.get(position).main.getTempMin().intValue() + "°C" + " / " +
                        forecastList.get(position).main.getTempMax().intValue() + "°C" + "\n" +
                        forecastList.get(position).weather.get(0).getDescription() + "  " +
                        DateUtil.convertUnixToHour(forecastList.get(position).dt_txt));
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


