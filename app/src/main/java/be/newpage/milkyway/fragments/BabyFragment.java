package be.newpage.milkyway.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.Date;

import be.newpage.milkyway.DatabaseHelper;
import be.newpage.milkyway.MyPreferences;
import be.newpage.milkyway.R;
import be.newpage.milkyway.Weight;
import be.newpage.milkyway.activities.MainActivity;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class BabyFragment extends RoboFragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    @InjectView(R.id.currentWeight)
    TextView currentWeightTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_baby, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyPreferences.setBirthDate(new Date("10 june 2014 2:13"));

        updateView();
    }

    private void updateView() {
        MainActivity mainActivity = (MainActivity) getActivity();
        Weight weight = mainActivity.getDatabaseHelper().queryForWeight(new Date("28 october 2014"));

        int currentWeight = -1;
        if (weight != null) {
            currentWeight = weight.getWeight();
        }

        String weightString = currentWeight == -1 ? "_____" : (String.valueOf(currentWeight) + "gr");
        currentWeightTextView.setText(getString(R.string.today_she_weights, weightString));
        currentWeightTextView.setOnClickListener(this);
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
    public void onClick(View view) {
        if (view.getId() == currentWeightTextView.getId()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Current weight (gr)");

            final EditText input = new EditText(getActivity());
            input.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String weightString = input.getText().toString();
                    try {
                        int value = Integer.parseInt(weightString);

                        MainActivity mainActivity = (MainActivity) getActivity();
                        RuntimeExceptionDao<Weight, Integer> dao = mainActivity.getDatabaseHelper().getWeightDao();
                        Date today = new Date();
                        today.setHours(0);
                        today.setMinutes(0);
                        today.setSeconds(0);
                        Weight weight = new Weight(today, value);
                        dao.create(weight);
                        Log.d(DatabaseHelper.class.getName(), "created new weight in database: " + weight);

                        updateView();
                        dialog.dismiss();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });

            alert.show();
        }
    }
}
