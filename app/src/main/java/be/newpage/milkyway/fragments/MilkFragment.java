package be.newpage.milkyway.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.Date;

import be.newpage.milkyway.DatabaseHelper;
import be.newpage.milkyway.Expression;
import be.newpage.milkyway.OnUpdateListener;
import be.newpage.milkyway.R;
import be.newpage.milkyway.activities.MainActivity;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class MilkFragment extends RoboFragment implements ViewPager.OnPageChangeListener, OnUpdateListener, View.OnClickListener {

    public static final int MAX_VOLUME = 250;
    public static final int STEPS = 5;
    private int cc = 0;

    @InjectView(R.id.title)
    TextView title;

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
        title.setOnClickListener(this);
        content.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        title.setText(getString(R.string.how_much_did_you_express));
        cc = 0;
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
        cc = STEPS * Math.round(value * ((float) MAX_VOLUME / (float) STEPS));
        content.setText(String.format("%d ml", cc));

        title.setText(getString(R.string.tap_to_confirm));
    }

    @Override
    public void onClick(View view) {
        if (cc != 0) {
            if (view.getId() == content.getId() || view.getId() == title.getId()) {
                MainActivity mainActivity = (MainActivity) getActivity();
                RuntimeExceptionDao<Expression, Integer> dao = mainActivity.getDatabaseHelper().getExpressionDao();
                Expression expression = new Expression(new Date(), cc);
                dao.create(expression);
                Log.d(DatabaseHelper.class.getName(), "created new expression in database: " + expression);

                cc = 0;
                title.setText(getString(R.string.confirmed));
            }
        }
    }
}
