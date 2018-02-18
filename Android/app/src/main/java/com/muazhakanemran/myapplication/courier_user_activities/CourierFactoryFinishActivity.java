package com.muazhakanemran.myapplication.courier_user_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.muazhakanemran.myapplication.R;
import com.muazhakanemran.myapplication.application.BaseApplication;
import com.muazhakanemran.myapplication.base_classes.ActivityBase;
import com.muazhakanemran.myapplication.events.GetUserJobListEvent;
import com.muazhakanemran.myapplication.events.GetUserJobListResponseEvent;
import com.muazhakanemran.myapplication.events.PostDoTransactionEvent;
import com.muazhakanemran.myapplication.events.PostFactoryTransactionEvent;
import com.muazhakanemran.myapplication.models.PostNewTransaction;
import com.muazhakanemran.myapplication.models.PostNewTransactionResponse;
import com.squareup.otto.Subscribe;

/**
 * Created by muazekici on 18.02.2018.
 */

public class CourierFactoryFinishActivity extends ActivityBase{

    @Override
    public int getContentLayout() {
        return R.layout.activity_factory_finish;
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


    TextView tvCoinAmount;
    EditText etFactoryKey;
    LinearLayout llSendFactoryKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

        GetUserJobListEvent event = new GetUserJobListEvent();
        event.setAndroid_id(((BaseApplication)getApplication()).getUserId());
        getBus().post(event);
    }


    private void initViews(){
        tvCoinAmount = findViewById(R.id.tv_deliver_factory_current_deposit);
        etFactoryKey = findViewById(R.id.et_deliver_factory_key);
        llSendFactoryKey = findViewById(R.id.ll_send_factory_key);

        llSendFactoryKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostNewTransaction transaction = new PostNewTransaction();
                transaction.setUser_id(((BaseApplication)getApplication()).getUserId());
                transaction.setTx_id(etFactoryKey.getText().toString());
                PostFactoryTransactionEvent event = new PostFactoryTransactionEvent();
                event.setTransactionObj(transaction);
                getBus().post(event);
            }
        });
    }

    @Subscribe
    public void onUserJobListResponseEvent(GetUserJobListResponseEvent event){
        tvCoinAmount.setText(event.getJobList().getCredits()+"");

    }

    @Subscribe
    public void onFactoryTransactionResponseEvent(PostNewTransactionResponse response){
        if(response == null){
            Toast.makeText(this,"İşlem gerçekleştirilemedi", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this,"İşlem başarıyla gerçekleştirildi", Toast.LENGTH_LONG).show();

            tvCoinAmount.setText(response.getCredits() + "");
        }

    }
}
