package com.muazhakanemran.myapplication.network_client;

import com.muazhakanemran.myapplication.models.BasicResponse;
import com.muazhakanemran.myapplication.models.Job;
import com.muazhakanemran.myapplication.models.JobList;
import com.muazhakanemran.myapplication.models.PostJobResponse;
import com.muazhakanemran.myapplication.models.PostNewTransaction;
import com.muazhakanemran.myapplication.models.PostNewTransactionResponse;
import com.muazhakanemran.myapplication.models.SubscribeNewUser;
import com.muazhakanemran.myapplication.models.VendorList;
import com.muazhakanemran.myapplication.models.VendorWorkRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by muazekici on 16.02.2018.
 */

public interface INetwork {


    @GET("vendors/find/")
    Call<VendorList> getNearVendorList(@Query("lat") double lat,@Query("lng") double lng,@Query("dist") int dist);

    @POST("users/createJob")
    Call<PostJobResponse> postNewJob(@Body Job job);

    @POST("users/subscribe")
    Call<BasicResponse> postNewUser(@Body SubscribeNewUser user);

    @GET("users/me/{android_id}")
    Call<JobList> getUserJobList(@Path("android_id") String android_id);

    @POST("users/recycle")
    Call<PostNewTransactionResponse> postNewTransactipn(@Body PostNewTransaction transaction);

    @POST("couriers/getVendorRoute")
    Call<VendorList> getNearVendorsAndFactory(@Body VendorWorkRequest request);


    @POST("couriers/finishJob")
    Call<PostNewTransactionResponse> postFactoryTransaction(@Body PostNewTransaction transaction);
}
