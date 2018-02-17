package com.muazhakanemran.myapplication.models;

import java.io.Serializable;

/**
 * Created by muazekici on 17.02.2018.
 */

public class Job implements Serializable {

    String _id;
    int quantity;
    double lat;
    double lng;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

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
}
