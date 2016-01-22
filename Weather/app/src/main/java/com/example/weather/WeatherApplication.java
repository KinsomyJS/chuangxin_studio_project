package com.example.weather;

import android.app.Application;

import com.thinkland.juheapi.common.CommonFun;

/**
 * Created by FJS0420 on 2016/1/17.
 */
public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonFun.initialize(getApplicationContext());
    }
}
