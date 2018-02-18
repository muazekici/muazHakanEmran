package com.muazhakanemran.myapplication.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muazekici on 17.02.2018.
 */

public class VendorList {

    int count;
    List<Vendor> vendors;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }
}
