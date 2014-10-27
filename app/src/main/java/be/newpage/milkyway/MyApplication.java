package be.newpage.milkyway;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;

public class MyApplication extends Application{

    private static MyApplication instance;

    public static Context getContext() {
        if (instance != null) {
            return instance.getApplicationContext();
        } else {
            return null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
