package com.muazhakanemran.myapplication.events;

import com.muazhakanemran.myapplication.models.VendorList;

/**
 * Created by muazekici on 17.02.2018.
 */

public class VendorListResponseEvent {

    VendorList vendorList;

    public VendorList getVendorList() {
        return vendorList;
    }

    public void setVendorList(VendorList vendorList) {
        this.vendorList = vendorList;
    }
}
