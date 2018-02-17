package com.muazhakanemran.myapplication.models;

import java.io.Serializable;

/**
 * Created by muazekici on 17.02.2018.
 */

public class SubscribeNewUser implements Serializable {

    String id;
    String name;

    public String id() {
        return id;
    }

    public void setAndroid_id(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
