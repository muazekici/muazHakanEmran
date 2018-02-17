package com.muazhakanemran.myapplication.normal_user_activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.muazhakanemran.myapplication.R;
import com.muazhakanemran.myapplication.application.BaseApplication;
import com.muazhakanemran.myapplication.base_classes.ActivityBase;
import com.muazhakanemran.myapplication.events.SubscribeNewUserEvent;
import com.muazhakanemran.myapplication.events.SubscribeNewUserResponseEvent;
import com.muazhakanemran.myapplication.models.SubscribeNewUser;
import com.squareup.otto.Subscribe;

/**
 * Created by muazekici on 17.02.2018.
 */

public class ChooseRoleActivity extends ActivityBase {


    LinearLayout llRoleNormalUser,llRoleCourierUser;
    EditText etUserName ;
    SharedPreferences.Editor editor;

    private int role = -1;


    private static final int COURIER = 1, NORMAL_USER = 2;

    @Override
    public boolean isUseToolbar() {
        return false;
    }

    @Override
    public boolean isUseLeftMenu() {
        return false;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_choose_role;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        role = preferences.getInt("UserRole",-1);
        if(role != -1){
            if(role == COURIER){

            }else if(role == NORMAL_USER){
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }
        }

        initViews();

    }

    private void initViews(){
        llRoleCourierUser = findViewById(R.id.ll_role_courier);
        llRoleNormalUser = findViewById(R.id.ll_role_normal_user);
        etUserName = findViewById(R.id.et_user_name);

        llRoleNormalUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("UserRole",NORMAL_USER);
                editor.commit();

                SubscribeNewUserEvent event = new SubscribeNewUserEvent();
                SubscribeNewUser user= new SubscribeNewUser();
                user.setName(etUserName.getText().toString());
                user.setAndroid_id(((BaseApplication)getApplication()).getUserId());
                event.setUser(user);
                getBus().post(event);



            }
        });

        llRoleCourierUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("UserRole",COURIER);
                editor.commit();
                /*Intent intent = new Intent(ChooseRoleActivity.this,MainActivity.class);
                startActivity(intent);*/
            }
        });


    }

    @Subscribe
    public void onSubscribeNewUser(SubscribeNewUserResponseEvent event){
        if(event.getResponse().getMessage().equals( "Created user successfully")){
            if(role == COURIER){

            }else if(role == NORMAL_USER){
                Intent intent = new Intent(ChooseRoleActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }
    }


}
