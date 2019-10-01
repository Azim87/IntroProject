package com.kubatov.androidthree.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.kubatov.androidthree.R;
import com.kubatov.androidthree.ui.MainActivity;
import com.kubatov.androidthree.ui.model.OnBoardModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnBoardAdapter extends PagerAdapter {
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.text_view_title)
    TextView textView;

    private List<OnBoardModel> models;
    private Context context;

    public OnBoardAdapter(List<OnBoardModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.item_onboard, null);

        ButterKnife.bind(this, view);

        imageView.setImageResource(models.get(position).getImage());
        textView.setText(models.get(position).getText());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        (container).removeView((View) object);
    }

}
