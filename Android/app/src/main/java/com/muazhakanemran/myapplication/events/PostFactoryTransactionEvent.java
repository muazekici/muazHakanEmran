package com.muazhakanemran.myapplication.events;

import com.muazhakanemran.myapplication.models.PostNewTransaction;

/**
 * Created by muazekici on 18.02.2018.
 */

public class PostFactoryTransactionEvent {

    PostNewTransaction transactionObj;

    public PostNewTransaction getTransactionObj() {
        return transactionObj;
    }

    public void setTransactionObj(PostNewTransaction transactionObj) {
        this.transactionObj = transactionObj;
    }
}
