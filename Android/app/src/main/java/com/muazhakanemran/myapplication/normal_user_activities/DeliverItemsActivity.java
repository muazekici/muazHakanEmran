package com.muazhakanemran.myapplication.normal_user_activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;
import com.muazhakanemran.myapplication.R;
import com.muazhakanemran.myapplication.adapters.AdapterTransactionHistory;
import com.muazhakanemran.myapplication.application.BaseApplication;
import com.muazhakanemran.myapplication.base_classes.ActivityBase;
import com.muazhakanemran.myapplication.events.GetUserJobListEvent;
import com.muazhakanemran.myapplication.events.GetUserJobListResponseEvent;
import com.muazhakanemran.myapplication.events.PostDoTransactionEvent;
import com.muazhakanemran.myapplication.events.PostDoTransactionResponseEvent;
import com.muazhakanemran.myapplication.models.PostNewTransaction;
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


        llSendKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostNewTransaction transaction = new PostNewTransaction();
                transaction.setUser_id(((BaseApplication)getApplication()).getUserId());
                transaction.setTx_id(etDeliverKey.getText().toString());
                PostDoTransactionEvent event = new PostDoTransactionEvent();
                event.setTransactionObj(transaction);
                getBus().post(event);
            }
        });
    }

    @Subscribe
    public void onUserJobListResponseEvent(GetUserJobListResponseEvent event){
        tvCurrentCoins.setText(event.getJobList().getCredits()+"");

    }

    @Subscribe
    public void onPostTransactionResponseEvent(PostDoTransactionResponseEvent event){
        if(event.getResponse() == null){
            Toast.makeText(this,"Girdiğiniz key yanlış.",Toast.LENGTH_LONG).show();
        }else {
            tvCurrentCoins.setText(event.getResponse().getCredits()+"");
            etDeliverKey.setText("");
            Toast.makeText(this,"İşleminiz başarıyla gerçekleştirildi.",Toast.LENGTH_LONG).show();

        }
    }

}
