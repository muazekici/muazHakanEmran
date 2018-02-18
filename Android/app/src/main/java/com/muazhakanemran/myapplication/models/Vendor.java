package com.muazhakanemran.myapplication.models;

import java.io.Serializable;

/**
 * Created by muazekici on 17.02.2018.
 */

public class Vendor implements Serializable {
    String name;
    int holding;
    int capacity;
    String _id;
    double lat;
    double lng;
    boolean isFactory;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isFactory() {
        return isFactory;
    }

    public void setFactory(boolean factory) {
        isFactory = factory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHolding() {
        return holding;
    }

    public void setHolding(int holding) {
        this.holding = holding;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
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
