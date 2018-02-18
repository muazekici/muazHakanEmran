package com.muazhakanemran.myapplication.network_client;

import com.muazhakanemran.myapplication.events.GetUserJobListResponseEvent;
import com.muazhakanemran.myapplication.events.GetVendorWorkListResponseEvent;
import com.muazhakanemran.myapplication.events.PostDoTransactionResponseEvent;
import com.muazhakanemran.myapplication.events.PostFactoryTransactionResponseEvent;
import com.muazhakanemran.myapplication.events.PostNewJobResponseEvent;
import com.muazhakanemran.myapplication.events.SubscribeNewUserResponseEvent;
import com.muazhakanemran.myapplication.events.VendorListResponseEvent;
import com.muazhakanemran.myapplication.models.BasicResponse;
import com.muazhakanemran.myapplication.models.GetNearVendorsRequest;
import com.muazhakanemran.myapplication.models.Job;
import com.muazhakanemran.myapplication.models.JobList;
import com.muazhakanemran.myapplication.models.PostJobResponse;
import com.muazhakanemran.myapplication.models.PostNewTransaction;
import com.muazhakanemran.myapplication.models.PostNewTransactionResponse;
import com.muazhakanemran.myapplication.models.SubscribeNewUser;
import com.muazhakanemran.myapplication.models.VendorList;
import com.muazhakanemran.myapplication.models.VendorWorkRequest;
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

    public void getUserJobList(String android_id){
        Call<JobList> call = apiServiceInstance.getUserJobList(android_id);

        call.enqueue(new Callback<JobList>() {
            @Override
            public void onResponse(Call<JobList> call, Response<JobList> response) {
                GetUserJobListResponseEvent event = new GetUserJobListResponseEvent();
                event.setJobList(response.body());
                mBus.post(event);
            }

            @Override
            public void onFailure(Call<JobList> call, Throwable t) {

            }
        });
    }


    public void postNewJob(Job req){
        Call<PostJobResponse> call = apiServiceInstance.postNewJob(req);

        call.enqueue(new Callback<PostJobResponse>() {
            @Override
            public void onResponse(Call<PostJobResponse> call, Response<PostJobResponse> response) {
                PostNewJobResponseEvent event = new PostNewJobResponseEvent();
                BasicResponse res = new BasicResponse();
                res.setMessage(response.raw().message().equals("Created") ? "OK" : "ERROR");
                event.setResponse(res);
                mBus.post(event);
            }

            @Override
            public void onFailure(Call<PostJobResponse> call, Throwable t) {

            }
        });
    }

    public void postNewUser(SubscribeNewUser user){

        Call<BasicResponse> call = apiServiceInstance.postNewUser(user);

        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                SubscribeNewUserResponseEvent event = new SubscribeNewUserResponseEvent();
                event.setResponse((BasicResponse) response.body());
                mBus.post(event);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

            }
        });
    }


    public void postNewTransaction(PostNewTransaction transaction){
        Call<PostNewTransactionResponse> call = apiServiceInstance.postNewTransactipn(transaction);

        call.enqueue(new Callback<PostNewTransactionResponse>() {
            @Override
            public void onResponse(Call<PostNewTransactionResponse> call, Response<PostNewTransactionResponse> response) {
                PostDoTransactionResponseEvent event = new PostDoTransactionResponseEvent();
                event.setResponse(response.body());
                mBus.post(event);
            }

            @Override
            public void onFailure(Call<PostNewTransactionResponse> call, Throwable t) {

            }
        });
    }


    public void getVendorWorkList(VendorWorkRequest request){
        Call<VendorList> call = apiServiceInstance.getNearVendorsAndFactory(request);

        call.enqueue(new Callback<VendorList>() {
            @Override
            public void onResponse(Call<VendorList> call, Response<VendorList> response) {
                GetVendorWorkListResponseEvent event = new GetVendorWorkListResponseEvent();
                event.setVendorList(response.body());
                mBus.post(event);
            }

            @Override
            public void onFailure(Call<VendorList> call, Throwable t) {

            }
        });
    }


    public void postFactoryTransaction(PostNewTransaction transaction){
        Call<PostNewTransactionResponse> call = apiServiceInstance.postFactoryTransaction(transaction);

        call.enqueue(new Callback<PostNewTransactionResponse>() {
            @Override
            public void onResponse(Call<PostNewTransactionResponse> call, Response<PostNewTransactionResponse> response) {

                PostFactoryTransactionResponseEvent event = new PostFactoryTransactionResponseEvent();
                event.setResponse(response.body());
                mBus.post(event);
            }

            @Override
            public void onFailure(Call<PostNewTransactionResponse> call, Throwable t) {

            }
        });
    }






}
