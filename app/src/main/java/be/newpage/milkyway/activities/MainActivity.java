package be.newpage.milkyway.activities;

import android.accounts.Account;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.Date;

import be.newpage.milkyway.DatabaseHelper;
import be.newpage.milkyway.Expression;
import be.newpage.milkyway.R;
import be.newpage.milkyway.Weight;
import be.newpage.milkyway.fragments.BabyFragment;
import be.newpage.milkyway.fragments.GraphFragment;
import be.newpage.milkyway.fragments.MilkFragment;
import be.newpage.milkyway.viewpager.DepthPageTransformer;
import be.newpage.milkyway.viewpager.ViewPagerCustomDuration;
import roboguice.activity.RoboFragmentActivity;


public class MainActivity extends RoboFragmentActivity {
    public static final int PAGE_INPUT_BABY = 0;
    public static final int PAGE_INPUT_MILK = 1;
    public static final int PAGE_GRAPH = 2;

    private static final int NUM_PAGES = 3;
    public ViewPagerCustomDuration viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    private DatabaseHelper databaseHelper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate a ViewPager and a PagerAdapter.
        viewPager = (ViewPagerCustomDuration) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setOffscreenPageLimit(NUM_PAGES - 1);

        /*
        RuntimeExceptionDao<Weight, Integer> dao = getDatabaseHelper().getWeightDao();
        Date today = new Date("28 october 2014");
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        Weight weight = new Weight(today, 6000);
        dao.create(weight);
        Log.d(DatabaseHelper.class.getName(), "created new weight in database: " + weight);

        today = new Date("29 october 2014");
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        weight = new Weight(today, 7000);
        dao.create(weight);
        Log.d(DatabaseHelper.class.getName(), "created new weight in database: " + weight);
        */
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setScrollDurationFactor(3);
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            viewPager.setScrollDurationFactor(1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;

            switch (position) {
                case PAGE_INPUT_BABY:
                    fragment = new BabyFragment();
                    break;
                case PAGE_INPUT_MILK:
                    fragment = new MilkFragment();
                    break;
                case PAGE_GRAPH:
                    fragment = new GraphFragment();
                    break;
                default:
                    return null;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
