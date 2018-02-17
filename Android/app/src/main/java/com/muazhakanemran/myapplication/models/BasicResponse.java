package com.muazhakanemran.myapplication.models;

import java.io.Serializable;

/**
 * Created by muazekici on 17.02.2018.
 */

public class BasicResponse implements Serializable{

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
