package com.kubatov.androidthree.ui.main.foreCast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kubatov.androidthree.R;

public class ForeCastAdapter extends RecyclerView.Adapter<ForeCastAdapter.ForeCastViewHolder> {
    private Context context;


    public ForeCastAdapter() {
    }

    @NonNull
    @Override
    public ForeCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_forecast, parent, false);
        return new ForeCastViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ForeCastViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ForeCastViewHolder extends RecyclerView.ViewHolder {
        public ForeCastViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}


