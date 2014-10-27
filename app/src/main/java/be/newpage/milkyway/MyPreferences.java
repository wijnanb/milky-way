package be.newpage.milkyway;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyPreferences {

    public static final String BABY_NAME = "baby_name";
    public static final String BORN_WEIGHT = "born_weight";
    public static final String CURRENT_WEIGHT = "current_weight";

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;

    public static SharedPreferences getInstance() {
        if (prefs == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
            editor = prefs.edit();
        }
        return prefs;
    }

    private static SharedPreferences.Editor getEditorInstance() {
        if (prefs == null || editor == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
            editor = prefs.edit();
        }
        return editor;
    }

    public static void clearAllPreferences() {
        if (prefs != null) {
            prefs.edit().clear().apply();
        }
        prefs = null;
        editor = null;
    }

    public static String getBabyName() {
        return "Lena";
    }

    public static int getBornWeight() {
        return 3315;
    }

    public static int getCurrentWeight() {
        return getInstance().getInt(CURRENT_WEIGHT, -1);
    }

    public static void setCurrentWeight(int weight) {
        getEditorInstance().putInt(CURRENT_WEIGHT, weight);
        getEditorInstance().apply();
    }
}
