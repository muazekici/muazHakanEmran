package com.muazhakanemran.myapplication.models;

import java.util.ArrayList;

/**
 * Created by muazekici on 17.02.2018.
 */

public class VendorList {

    int count;
    ArrayList<Vendor> vendors;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(ArrayList<Vendor> vendors) {
        this.vendors = vendors;
    }
}
