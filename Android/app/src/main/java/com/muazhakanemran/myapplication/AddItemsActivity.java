package com.muazhakanemran.myapplication;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.muazhakanemran.myapplication.application.BaseApplication;
import com.muazhakanemran.myapplication.base_classes.ActivityBase;
import com.muazhakanemran.myapplication.events.GetNearVendingMachinesEvent;
import com.muazhakanemran.myapplication.events.PostNewJobEvent;
import com.muazhakanemran.myapplication.models.GetNearVendorsRequest;
import com.muazhakanemran.myapplication.models.Job;

/**
 * Created by muazekici on 17.02.2018.
 */

public class AddItemsActivity extends ActivityBase implements OnMapReadyCallback {


    private SupportMapFragment mapFragment;

    private FusedLocationProviderClient mFusedLocationClient;

    private GoogleMap itemLocationMap;

    private Location itemLocation;

    private EditText etJobQuantity;

    private LinearLayout llContinue;


    @Override
    public int getContentLayout() {
        return R.layout.activity_add_item;
    }


    @Override
    public int getToolbarLayout() {
        return R.layout.test_toolbar_layout;
    }

    @Override
    public boolean isUseLeftMenu() {
        return false;
    }

    @Override
    public boolean isUseToolbar() {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
    }


    private void initComponents(){

        itemLocation = new Location("test");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.items_location);

        mapFragment.getMapAsync(this);

        llContinue = findViewById(R.id.ll_continue);
        etJobQuantity = findViewById(R.id.et_item_number);

        llContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Job jobItem = new Job();
                jobItem.setId(((BaseApplication)getApplication()).getUserId());
                jobItem.setLat(itemLocation.getLatitude());
                jobItem.setLng(itemLocation.getLongitude());
                jobItem.setQuantity(Integer.parseInt(etJobQuantity.getText().toString()));

                PostNewJobEvent event = new PostNewJobEvent();
                event.setJob(jobItem);
                getBus().post(event);
            }
        });


    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLatitude(),location.getLongitude()))
                        .title("current location"))
                        .setDraggable(true);

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));

                itemLocation.setLongitude(location.getLongitude());
                itemLocation.setLatitude(location.getLatitude());
            }


        });
        itemLocationMap = googleMap;
        itemLocationMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                itemLocation.setLatitude(marker.getPosition().latitude);
                itemLocation.setLongitude(marker.getPosition().longitude);
            }
        });
    }
}
