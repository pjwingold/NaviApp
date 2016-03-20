package com.inteliment.pjwin.naviapp;

import android.app.Application;

import com.jude.beam.Beam;
import com.jude.http.RequestManager;

/**
 * Created by hans on 20-Mar-16.
 */
public class NaviApp extends Application {
    private static NaviApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        RequestManager.getInstance().init(this);//A Volley Wrapper
        Beam.init(this);//MVP pattern simplifier
    }

    public static NaviApp getInstance() {
        return mApp;
    }
}
