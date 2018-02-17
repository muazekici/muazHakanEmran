package com.muazhakanemran.myapplication.courier_user_activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.muazhakanemran.myapplication.R;
import com.muazhakanemran.myapplication.base_classes.ActivityBase;
import com.muazhakanemran.myapplication.events.GetNearVendingMachinesEvent;
import com.muazhakanemran.myapplication.models.GetNearVendorsRequest;
import com.muazhakanemran.myapplication.models.Vendor;

/**
 * Created by muazekici on 17.02.2018.
 */

public class ChooseLocationActivity extends ActivityBase  implements OnMapReadyCallback{
    @Override
    public int getContentLayout() {
        return R.layout.activity_choose_location;
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

    private SupportMapFragment mapFragment;

    private FusedLocationProviderClient mFusedLocationClient;

    Location lastLocation;

    GoogleMap vendorMap;

    LinearLayout llSelectChoosenLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();

    }

    private void initComponents(){
        lastLocation = new Location("test");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.items_location);

        mapFragment.getMapAsync(this);

        llSelectChoosenLocation = findViewById(R.id.ll_courier_select_location);
        llSelectChoosenLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("lat",lastLocation.getLatitude());
                returnIntent.putExtra("lng",lastLocation.getLongitude());
                setResult(Activity.RESULT_OK,returnIntent);

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

                lastLocation.setLongitude(location.getLongitude());
                lastLocation.setLatitude(location.getLatitude());
            }


        });
        vendorMap = googleMap;
        vendorMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                lastLocation.setLatitude(marker.getPosition().latitude);
                lastLocation.setLongitude(marker.getPosition().longitude);
            }
        });
    }


}
