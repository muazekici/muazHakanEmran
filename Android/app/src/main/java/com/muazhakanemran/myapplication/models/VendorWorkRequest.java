package com.muazhakanemran.myapplication.models;

import java.io.Serializable;

/**
 * Created by muazekici on 18.02.2018.
 */

public class VendorWorkRequest implements Serializable {

    double lat;
    double lng;
    int amount;
    String user_id;


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
