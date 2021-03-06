package com.muazhakanemran.myapplication.normal_user_activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

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

public class CurrentDepositActivity extends ActivityBase {


    @Override
    public int getContentLayout() {
        return R.layout.activity_current_deposit;
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



    TextView tvCurrentDeposit;
    RecyclerView rvJobList;
    AdapterTransactionHistory jobListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        GetUserJobListEvent event = new GetUserJobListEvent();
        event.setAndroid_id(((BaseApplication)getApplication()).getUserId());
        getBus().post(event);
    }

    private void initViews(){
        rvJobList = findViewById(R.id.rv_job_list);
        tvCurrentDeposit = findViewById(R.id.tv_current_deposit);

    }

    @Subscribe
    public void onUserJobListResponseEvent(GetUserJobListResponseEvent event){
        tvCurrentDeposit.setText(event.getJobList().getCredits()+"");
        jobListAdapter = new AdapterTransactionHistory(this,event.getJobList().getOpenJobs(),event.getJobList().getClosedJobs());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        rvJobList.setLayoutManager(layoutManager);
        rvJobList.setAdapter(jobListAdapter);
    }
}
