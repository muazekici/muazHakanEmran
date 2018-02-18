package com.muazhakanemran.myapplication.network_client;

import android.content.Context;

import com.muazhakanemran.myapplication.events.GetNearVendingMachinesEvent;
import com.muazhakanemran.myapplication.events.GetUserJobListEvent;
import com.muazhakanemran.myapplication.events.GetVendorWorkListEvent;
import com.muazhakanemran.myapplication.events.PostDoTransactionEvent;
import com.muazhakanemran.myapplication.events.PostFactoryTransactionEvent;
import com.muazhakanemran.myapplication.events.PostNewJobEvent;
import com.muazhakanemran.myapplication.events.PostNewJobResponseEvent;
import com.muazhakanemran.myapplication.events.SubscribeNewUserEvent;
import com.muazhakanemran.myapplication.models.PostNewTransaction;
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

    @Subscribe
    public void onSubscribeNewUserEvent(SubscribeNewUserEvent event){
        sNetworkClient.postNewUser(event.getUser());
    }

    @Subscribe
    public void onGetUserJobListEvent(GetUserJobListEvent event){
        sNetworkClient.getUserJobList(event.getAndroid_id());
    }

    @Subscribe
    public void onPostNewTransaction(PostDoTransactionEvent event){
        sNetworkClient.postNewTransaction(event.getTransactionObj());
    }

    @Subscribe
    public void onGetVendorWorkListEvent(GetVendorWorkListEvent event){
        sNetworkClient.getVendorWorkList(event.getRequest());
    }

    @Subscribe
    public void onPostNewFactoryTransactionEvent(PostFactoryTransactionEvent event){
        sNetworkClient.postFactoryTransaction(event.getTransactionObj());
    }

}
