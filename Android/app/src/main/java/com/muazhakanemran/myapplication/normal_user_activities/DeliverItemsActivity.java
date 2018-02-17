package com.muazhakanemran.myapplication.normal_user_activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;
import com.muazhakanemran.myapplication.R;
import com.muazhakanemran.myapplication.adapters.AdapterTransactionHistory;
import com.muazhakanemran.myapplication.application.BaseApplication;
import com.muazhakanemran.myapplication.base_classes.ActivityBase;
import com.muazhakanemran.myapplication.events.GetUserJobListEvent;
import com.muazhakanemran.myapplication.events.GetUserJobListResponseEvent;
import com.squareup.otto.Subscribe;

/**
 * Created by muazekici on 17.02.2018.
 */

public class DeliverItemsActivity extends ActivityBase {


    @Override
    public int getContentLayout() {
        return R.layout.activity_deliver_objects;
    }


    @Override
    public int getToolbarLayout() {
        return R.layout.toolbar_layout_empty;
    }

    @Override
    public boolean isUseLeftMenu() { return false;    }

    @Override
    public boolean isUseBackIcon() { return true;    }

    @Override
    public boolean isUseToolbar() {
        return true;
    }


    TextView tvCurrentCoins;
    EditText etDeliverKey;
    LinearLayout llSendKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        GetUserJobListEvent event = new GetUserJobListEvent();
        event.setAndroid_id(((BaseApplication)getApplication()).getUserId());
        getBus().post(event);
    }


    private void initViews(){
        tvCurrentCoins = findViewById(R.id.tv_deliver_current_deposit);
        etDeliverKey = findViewById(R.id.et_deliver_automat_key);
        llSendKey = findViewById(R.id.ll_send_key);
    }

    @Subscribe
    public void onUserJobListResponseEvent(GetUserJobListResponseEvent event){
        tvCurrentCoins.setText(event.getJobList().getCredits()+"");

    }
}
