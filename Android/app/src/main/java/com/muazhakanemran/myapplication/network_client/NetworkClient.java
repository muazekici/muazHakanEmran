package com.muazhakanemran.myapplication.network_client;

import com.muazhakanemran.myapplication.events.VendorListResponseEvent;
import com.muazhakanemran.myapplication.models.GetNearVendorsRequest;
import com.muazhakanemran.myapplication.models.Job;
import com.muazhakanemran.myapplication.models.PostJobResponse;
import com.muazhakanemran.myapplication.models.VendorList;
import com.squareup.otto.Bus;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by muazekici on 16.02.2018.
 */

public class NetworkClient {

    private static final String BASE_URL = "https://recycler-hackathon.herokuapp.com/";



    private static NetworkClient mNetworkClient;
    private static Retrofit mRetrofit;
    private static INetwork apiServiceInstance;

    private Bus mBus;

    public static NetworkClient getClient(Bus bus){
        if(mNetworkClient == null){
            mNetworkClient = new NetworkClient(bus);
        }
        return mNetworkClient;
    }

    private  NetworkClient(Bus bus){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpClient client = httpClient.build();



        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiServiceInstance = mRetrofit.create(INetwork.class);

        this.mBus = bus;
    }



    public void getNearVendingMachines(final GetNearVendorsRequest req){
        Call<VendorList> call = apiServiceInstance.getNearVendorList(req.getLat(),req.getLng(),req.getDist());

        call.enqueue(new Callback<VendorList>() {
            @Override
            public void onResponse(Call<VendorList> call, Response<VendorList> response) {
                VendorListResponseEvent event = new VendorListResponseEvent();
                event.setVendorList((VendorList) response.body());
                mBus.post(event);
            }

            @Override
            public void onFailure(Call<VendorList> call, Throwable t) {

            }
        });
    }


    public void postNewJob(Job req){
        Call<PostJobResponse> call = apiServiceInstance.postNewJob(req);

        call.enqueue(new Callback<PostJobResponse>() {
            @Override
            public void onResponse(Call<PostJobResponse> call, Response<PostJobResponse> response) {

            }

            @Override
            public void onFailure(Call<PostJobResponse> call, Throwable t) {

            }
        });
    }






}
