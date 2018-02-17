package com.muazhakanemran.myapplication.events;

import com.muazhakanemran.myapplication.models.GetNearVendorsRequest;

/**
 * Created by muazekici on 17.02.2018.
 */

public class GetNearVendingMachinesEvent {

    GetNearVendorsRequest request;

    public GetNearVendorsRequest getRequest() {
        return request;
    }

    public void setRequest(GetNearVendorsRequest request) {
        this.request = request;
    }
}
