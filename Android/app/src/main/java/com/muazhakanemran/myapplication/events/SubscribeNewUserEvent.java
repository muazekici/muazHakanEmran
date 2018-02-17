package com.muazhakanemran.myapplication.events;

import com.muazhakanemran.myapplication.models.SubscribeNewUser;

/**
 * Created by muazekici on 17.02.2018.
 */

public class SubscribeNewUserEvent {

    SubscribeNewUser user;

    public SubscribeNewUser getUser() {
        return user;
    }

    public void setUser(SubscribeNewUser user) {
        this.user = user;
    }
}
