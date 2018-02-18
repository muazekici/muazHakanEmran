package com.muazhakanemran.myapplication.events;

import com.muazhakanemran.myapplication.models.PostNewTransaction;
import com.muazhakanemran.myapplication.models.PostNewTransactionResponse;

/**
 * Created by muazekici on 18.02.2018.
 */

public class PostFactoryTransactionResponseEvent {

    PostNewTransactionResponse response;

    public PostNewTransactionResponse getResponse() {
        return response;
    }

    public void setResponse(PostNewTransactionResponse response) {
        this.response = response;
    }
}
