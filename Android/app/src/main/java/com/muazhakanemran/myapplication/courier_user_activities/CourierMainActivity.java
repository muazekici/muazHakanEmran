package com.muazhakanemran.myapplication.courier_user_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.muazhakanemran.myapplication.R;
import com.muazhakanemran.myapplication.base_classes.ActivityBase;
import com.muazhakanemran.myapplication.normal_user_activities.MainActivity;

/**
 * Created by muazekici on 17.02.2018.
 */

public class CourierMainActivity extends ActivityBase {


    @Override
    public int getContentLayout() {
        return R.layout.activity_courier_main;
    }


    @Override
    public int getToolbarLayout() {
        return R.layout.courier_toolbar_layout;
    }

    @Override
    public boolean isUseLeftMenu() { return false;    }

    @Override
    public boolean isUseBackIcon() { return false;    }

    @Override
    public boolean isUseToolbar() {
        return true;
    }


    LinearLayout llCourierHome,llCourierStation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews(){
        llCourierHome = findViewById(R.id.ll_collect_from_home);
        llCourierStation = findViewById(R.id.ll_collect_from_stations);


        View view = getmToolbar().findViewById(R.id.iv_toolbar_right_item);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourierMainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        llCourierStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourierMainActivity.this, CourierStartStationWorkActivity.class);
                startActivity(intent);
            }
        });

        llCourierHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourierMainActivity.this, CourierFactoryFinishActivity.class);
                startActivity(intent);
            }
        });
    }
}
