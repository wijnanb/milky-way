package be.newpage.milkyway.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import be.newpage.milkyway.Expression;
import be.newpage.milkyway.MyPreferences;
import be.newpage.milkyway.R;
import be.newpage.milkyway.activities.MainActivity;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class GraphFragment extends RoboFragment implements ViewPager.OnPageChangeListener {

    @InjectView(R.id.my_recycler_view)
    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_graph, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ((MainActivity) getActivity()).viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == MainActivity.PAGE_GRAPH) {
            // specify an adapter (see also next example)
            MainActivity mainActivity = (MainActivity) getActivity();
            List<Expression> expressions = mainActivity.getDatabaseHelper().queryForExpressions();
            mAdapter = new MyAdapter(expressions);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public static int getLenaVolumePerDay(Date dateOfBirth, int currentWeight) {
        return getLenaVolumePerDay(dateOfBirth, currentWeight, new Date());
    }

    public static int getLenaVolumePerDay(Date dateOfBirth, int currentWeight, Date today) {
        long diff = today.getTime() - dateOfBirth.getTime();
        int daysOld = (int) (diff / (1000 * 60 * 60 * 24));
        int monthsOld = daysOld / 30;

        float weight = ((float)currentWeight) / 1000.0f;

        int volumePerKilo = -1;
        switch (monthsOld) {
            case 0:
            case 1:
                volumePerKilo = 150;
                break;
            case 2:
                volumePerKilo = 140;
                break;
            case 3:
                volumePerKilo = 130;
                break;
            case 4:
                volumePerKilo = 120;
                break;
            case 5:
                volumePerKilo = 110;
                break;
            case 6:
                volumePerKilo = 100;
        }

        if (volumePerKilo != -1) {
            return (int) Math.floor(volumePerKilo * weight);
        } else {
            if (monthsOld <= 8) {
                return 700;
            } else if (monthsOld <= 11) {
                return 540;
            } else if (monthsOld <= 18) {
                return 500;
            } else if (monthsOld <= 24) {
                return 450;
            }
        }

        return -1;
    }
}
