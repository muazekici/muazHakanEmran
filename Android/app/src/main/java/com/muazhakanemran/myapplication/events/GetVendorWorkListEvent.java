package com.muazhakanemran.myapplication.events;

import com.muazhakanemran.myapplication.models.VendorWorkRequest;

/**
 * Created by muazekici on 18.02.2018.
 */

public class GetVendorWorkListEvent {

    VendorWorkRequest request;

    public VendorWorkRequest getRequest() {
        return request;
    }

    public void setRequest(VendorWorkRequest request) {
        this.request = request;
    }
}
