package com.muazhakanemran.myapplication.application;

import android.app.Application;
import android.provider.Settings;

import com.muazhakanemran.myapplication.bus_provider.BusProvider;
import com.muazhakanemran.myapplication.network_client.NetworkManager;
import com.squareup.otto.Bus;

/**
 * Created by muazekici on 16.02.2018.
 */

public class BaseApplication  extends Application{


    private NetworkManager mNetworkManager;
    private Bus mBus = BusProvider.getInstance();

    private static String userId;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetworkManager = new NetworkManager(this,mBus);
        mBus.register(mNetworkManager);
        mBus.register(this);
        userId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public Bus getBus(){
        return mBus;
    }

    public String getUserId(){
        return userId;
    }
}
