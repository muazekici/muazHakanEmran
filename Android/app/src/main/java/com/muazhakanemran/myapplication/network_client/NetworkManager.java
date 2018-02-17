package com.muazhakanemran.myapplication.network_client;

import android.content.Context;

import com.muazhakanemran.myapplication.events.GetNearVendingMachinesEvent;
import com.muazhakanemran.myapplication.events.PostNewJobEvent;
import com.muazhakanemran.myapplication.events.PostNewJobResponseEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by muazekici on 16.02.2018.
 */

public class NetworkManager {


    private Context mContext;
    private Bus mBus;
    private NetworkClient sNetworkClient;

    public NetworkManager(Context context,Bus bus){
        this.mBus = bus;
        this.mContext = context;
        sNetworkClient = NetworkClient.getClient(mBus);
    }







    @Subscribe
    public void onGetNearVendorListEvent(GetNearVendingMachinesEvent event){
        sNetworkClient.getNearVendingMachines(event.getRequest());
    }

    @Subscribe
    public void onPostNewJob(PostNewJobEvent event){
        sNetworkClient.postNewJob(event.getJob());
    }

}
