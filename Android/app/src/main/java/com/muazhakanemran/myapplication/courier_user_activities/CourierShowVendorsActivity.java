package com.muazhakanemran.myapplication.courier_user_activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.muazhakanemran.myapplication.R;
import com.muazhakanemran.myapplication.application.BaseApplication;
import com.muazhakanemran.myapplication.base_classes.ActivityBase;
import com.muazhakanemran.myapplication.events.GetVendorWorkListEvent;
import com.muazhakanemran.myapplication.events.GetVendorWorkListResponseEvent;
import com.muazhakanemran.myapplication.models.Vendor;
import com.muazhakanemran.myapplication.models.VendorList;
import com.muazhakanemran.myapplication.models.VendorWorkRequest;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Created by muazekici on 18.02.2018.
 */

public class CourierShowVendorsActivity extends ActivityBase implements OnMapReadyCallback {

    @Override
    public int getContentLayout() {
        return R.layout.activity_courier_show_vendors;
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



    private SupportMapFragment mapFragment;

    private FusedLocationProviderClient mFusedLocationClient;

    private GoogleMap vendorWorkMap;

    private Location lastLocation;

    private Location choosenLocation;

    private int choosenAmount;

    VendorList vendorList;

    LinearLayout llSearchRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        choosenAmount = getIntent().getExtras().getInt("amount");
        choosenLocation = (Location) getIntent().getParcelableExtra("location");

        initComponents();
    }

    private void initComponents(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_show_vendors_list);

        mapFragment.getMapAsync(this);

        lastLocation = new Location("current");

        llSearchRoute = findViewById(R.id.ll_courier_station_map_route);

        llSearchRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(vendorList == null){
                    return;
                }
                Uri mapUri = createURIforMap();
                Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
                intent.setPackage("com.google.android.apps.maps");

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    try {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                        startActivity(unrestrictedIntent);
                    } catch (ActivityNotFoundException innerEx) {
                        Toast.makeText(CourierShowVendorsActivity.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        vendorWorkMap = googleMap;
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLatitude(),location.getLongitude()))
                        .title("current location"));

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));

                lastLocation.setLongitude(location.getLongitude());
                lastLocation.setLatitude(location.getLatitude());
            }


        });


        VendorWorkRequest req = new VendorWorkRequest();
        req.setAmount(choosenAmount);
        req.setLat(choosenLocation.getLongitude());
        req.setLng(choosenLocation.getLatitude());
        req.setUser_id(((BaseApplication)getApplication()).getUserId());

        GetVendorWorkListEvent event = new GetVendorWorkListEvent();
        event.setRequest(req);

        getBus().post(event);
    }



    @Subscribe
    public void onVendorWorkListResponseEvent(GetVendorWorkListResponseEvent event){
        if(event.getVendorList().getCount() == 0 ){
            Toast.makeText(this,"Hiç bir istasyon bulunamadı",Toast.LENGTH_LONG).show();
        }else{
            this.vendorList = event.getVendorList();
            for(Vendor v : event.getVendorList().getVendors()){
                vendorWorkMap.addMarker(new MarkerOptions()
                        .position(new LatLng(v.getLat(),v.getLng()))
                        .title(v.getName())
                        .snippet(v.getHolding()+"/"+v.getCapacity()))
                        .setIcon(BitmapDescriptorFactory.fromResource(v.isFactory() ? R.drawable.ic_factory : R.drawable.ic_recycler ));

/*
                        positionList.add(new LatLng(v.getLat(),v.getLng()));
*/

            }
        }
    }


    public Uri createURIforMap(){
        String s = "https://www.google.com/maps/dir/?api=1&";
        s+= "origin="+choosenLocation.getLongitude()+","+choosenLocation.getLatitude()+"&";
        s+= "destination="+vendorList.getVendors().get(vendorList.getVendors().size()-1).getLat()+","
                +vendorList.getVendors().get(vendorList.getVendors().size()-1).getLng()+"&";

        s+="waypoints=";

        for(int i = 0; i < vendorList.getVendors().size()-1 ; i++ ){
            s+= vendorList.getVendors().get(i).getLat()+","+vendorList.getVendors().get(i).getLng()+"|";
        }
        s = s.substring(0,s.length()-1);
        s+="&travelmode=driving";

        return Uri.parse(s);
    }


}
