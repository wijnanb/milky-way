package be.newpage.milkyway.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import be.newpage.milkyway.OnUpdateListener;
import be.newpage.milkyway.R;
import be.newpage.milkyway.activities.MainActivity;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class MilkFragment extends RoboFragment implements ViewPager.OnPageChangeListener, OnUpdateListener {

    public static final int MAX_VOLUME = 250;
    public static final int STEPS = 5;

    @InjectView(R.id.contents)
    MilkView contents;

    @InjectView(R.id.content)
    TextView content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_milk, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contents.setListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == MainActivity.PAGE_INPUT_BABY) {

        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void update(float value) {
        int cc = STEPS * Math.round(value * ((float) MAX_VOLUME / (float) STEPS));
        content.setText(String.format("%d ml", cc));
    }
}
