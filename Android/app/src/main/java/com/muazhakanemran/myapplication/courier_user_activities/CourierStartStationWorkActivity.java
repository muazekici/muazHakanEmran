package com.muazhakanemran.myapplication.courier_user_activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.muazhakanemran.myapplication.R;
import com.muazhakanemran.myapplication.base_classes.ActivityBase;

/**
 * Created by muazekici on 17.02.2018.
 */

public class CourierStartStationWorkActivity extends ActivityBase {

    @Override
    public int getContentLayout() {
        return R.layout.activity_courier_station_work;
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

    LinearLayout llSearch, llChooseLocation;
    EditText etAmount;

    Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

    }

    private void initViews(){
        llSearch = findViewById(R.id.ll_courier_station_search);
        llChooseLocation = findViewById(R.id.ll_courier_station_location_select);
        llChooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourierStartStationWorkActivity.this,ChooseLocationActivity.class);
                startActivityForResult(intent,314);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 314){
            if(resultCode == Activity.RESULT_OK ){
                loc = new Location("courier");
                loc.setLongitude(data.getDoubleExtra("lat",0));
                loc.setLatitude(data.getDoubleExtra("lng",0));
            }
        }
    }
}
