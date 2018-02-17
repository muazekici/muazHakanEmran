package com.muazhakanemran.myapplication.network_client;

import com.muazhakanemran.myapplication.models.Job;
import com.muazhakanemran.myapplication.models.PostJobResponse;
import com.muazhakanemran.myapplication.models.VendorList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by muazekici on 16.02.2018.
 */

public interface INetwork {


    @GET("vendors/find/")
    Call<VendorList> getNearVendorList(@Query("lat") double lat,@Query("lng") double lng,@Query("dist") int dist);

    @POST("users/createJob")
    Call<PostJobResponse> postNewJob(@Body Job job);
}
