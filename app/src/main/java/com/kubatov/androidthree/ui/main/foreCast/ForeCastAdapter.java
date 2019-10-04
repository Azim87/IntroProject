package com.kubatov.androidthree.ui.main.foreCast;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kubatov.androidthree.R;
import com.kubatov.androidthree.data.model.forecast_model.Forecast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForeCastAdapter extends RecyclerView.Adapter<ForeCastAdapter.ForeCastViewHolder> {

    private ArrayList<Forecast> list;


    public ForeCastAdapter(ArrayList<Forecast> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ForeCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForeCastViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ForeCastViewHolder holder, int position) {

        Log.d("ololo", " adapter " + list.get(0).getList().get(0).main.getTempMax().intValue());


        holder.tempTextView.setText(list.get(0).getCity().getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ForeCastViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.temp)
        TextView tempTextView;

        private ForeCastViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


