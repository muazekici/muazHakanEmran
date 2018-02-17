package com.muazhakanemran.myapplication.events;

import com.muazhakanemran.myapplication.models.BasicResponse;

/**
 * Created by muazekici on 17.02.2018.
 */

public class SubscribeNewUserResponseEvent {

    BasicResponse response;

    public BasicResponse getResponse() {
        return response;
    }

    public void setResponse(BasicResponse response) {
        this.response = response;
    }
}
