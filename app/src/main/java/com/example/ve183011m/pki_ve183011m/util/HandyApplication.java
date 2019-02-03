package com.example.ve183011m.pki_ve183011m.util;

import android.app.Application;
import android.content.Context;

public class HandyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        HandyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return HandyApplication.context;
    }

}
