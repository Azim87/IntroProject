package com.kubatov.androidthree.ui.onboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import com.kubatov.androidthree.R;
import com.kubatov.androidthree.data.model.OnBoardModel;
import com.kubatov.androidthree.ui.main.viewpager.CurrentWeatherFragment;
import java.util.List;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OnBoardActivity extends AppCompatActivity {

    @BindView(R.id.main_view_pager)
    ViewPager viewPager;

    @BindView(R.id.linear_layout)
    LinearLayout linearLayoutDots;

    @BindView(R.id.button_next)
    Button nextButton;

    @BindView(R.id.text_view_skip)
    TextView textView;

    private ImageView[] mDots;
    private int dotsCount;

    public static void start(Context context) {
        context.startActivity(new Intent(context, OnBoardActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        ButterKnife.bind(this);

        setViewPager();
    }

    //region ViewPager
    private void setViewPager() {
        OnBoardAdapter adapter = new OnBoardAdapter(getData());
        viewPager.setAdapter(adapter);
        dotsCount = adapter.getCount();

        textView.setOnClickListener(v -> {
            viewPager.setCurrentItem(3, true);
        });

        nextButton.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() == adapter.getCount() - 1) {
                CurrentWeatherFragment.start(this);
                finish();
            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });
        slideDotes();
    }

    private List<OnBoardModel> getData(){
        List<OnBoardModel> modelList = new ArrayList<>();
        modelList.add(new OnBoardModel(R.drawable.study, R.string.intro_name_1));
        modelList.add(new OnBoardModel(R.drawable.update, R.string.intro_name_2));
        modelList.add(new OnBoardModel(R.drawable.delete, R.string.intro_name_3));
        modelList.add(new OnBoardModel(R.drawable.thanks, R.string.intro_name_4));
        return modelList;
    }
    //endregion

    //region ViewPager slide Dots
    private void slideDotes() {
        if (linearLayoutDots != null) {
            linearLayoutDots.removeAllViews();
        }
        mDots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            mDots[i] = new ImageView(this);
            mDots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonactive_dots));

            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lParams.setMargins(4, 0, 4, 0);
            linearLayoutDots.addView(mDots[i], lParams);
            mDots[0].setImageDrawable(getResources().getDrawable(R.drawable.active_dots));

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    for (int i = 0; i < dotsCount; i++) {
                        mDots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonactive_dots));
                    }
                    mDots[position].setImageDrawable(getResources().getDrawable(R.drawable.active_dots));
                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                        case 1:
                        case 2:
                            nextButton.setText("Далее");
                            textView.setText("Пропустить");
                            break;
                        case 3:
                            nextButton.setText("Далее");
                            textView.setText("Готово");
                            break;
                    }
                }
                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }
    //endregion
}
