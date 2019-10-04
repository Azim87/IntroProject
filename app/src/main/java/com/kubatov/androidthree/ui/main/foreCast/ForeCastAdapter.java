package com.kubatov.androidthree.ui.main.foreCast;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kubatov.androidthree.R;
import com.kubatov.androidthree.data.model.forecast_model.Forecast;
import com.kubatov.androidthree.util.DateUtil;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ForeCastAdapter extends RecyclerView.Adapter<ForeCastAdapter.ForeCastViewHolder> {
    private ArrayList<Forecast> list;

     ForeCastAdapter(ArrayList<Forecast> list) {
        this.list = list;
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

        holder.tempTextView.setText("Today" + "\n"
                + DateUtil.convertUnixToDate(list.get(0).getList().get(0).dt) + "\n"
                + list.get(0).getList().get(0).weather.get(0).getDescription() + "\n"
                + list.get(0).getList().get(0).main.getTempMin().intValue()+ "°C" + "/"
                + list.get(0).getList().get(0).main.getTempMax().intValue()+ "°C");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ForeCastViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.temp)
        TextView tempTextView;

        private ForeCastViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


