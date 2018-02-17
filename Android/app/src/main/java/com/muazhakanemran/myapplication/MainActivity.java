package com.muazhakanemran.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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
import com.muazhakanemran.myapplication.base_classes.ActivityBase;
import com.muazhakanemran.myapplication.events.GetNearVendingMachinesEvent;
import com.muazhakanemran.myapplication.events.VendorListResponseEvent;
import com.muazhakanemran.myapplication.models.GetNearVendorsRequest;
import com.muazhakanemran.myapplication.models.Vendor;
import com.muazhakanemran.myapplication.models.VendorList;
import com.squareup.otto.Subscribe;

public class MainActivity extends ActivityBase implements OnMapReadyCallback {


    private SupportMapFragment mapFragment;

    private FusedLocationProviderClient mFusedLocationClient;

    Location lastLocation;

    VendorList mVendorList;

    GoogleMap vendorMap;

    ProgressBar progressBar;

    LinearLayout llTransparentBg;

    ImageView ivAddItems;

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }


    @Override
    public int getToolbarLayout() {
        return R.layout.test_toolbar_layout;
    }

    @Override
    public boolean isUseLeftMenu() {
        return true;
    }

    @Override
    public boolean isUseToolbar() {
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        initComponents();
    }

    private void initComponents() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           return;
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                lastLocation = location;

                if(location == null){
                    return;
                }
                GetNearVendorsRequest req = new GetNearVendorsRequest();
                req.setDist(3000);
                req.setLat(lastLocation.getLatitude());
                req.setLng(lastLocation.getLongitude());

                GetNearVendingMachinesEvent event = new GetNearVendingMachinesEvent();
                event.setRequest(req);

                progressBar.setVisibility(View.VISIBLE);
                llTransparentBg.setVisibility(View.VISIBLE);
                getBus().post(event);
            }
        });


    }

    private void initViews(){
        llTransparentBg = findViewById(R.id.ll_progress_translucent_bg);
        progressBar =  findViewById(R.id.pgr_progress_item);
        ivAddItems = findViewById(R.id.iv_add_new_items);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.vendorMap = googleMap;


        initializeVendorLocations();

    }

    @Subscribe
    public void onVendorListResponse(VendorListResponseEvent event){
        mVendorList = event.getVendorList();

      initializeVendorLocations();
    }

    private void initializeVendorLocations(){
        if(this.vendorMap != null && this.mVendorList != null){
            for(Vendor v : mVendorList.getVendors()){
                vendorMap.addMarker(new MarkerOptions()
                        .position(new LatLng(v.getLat(),v.getLng()))
                        .title(v.getName())
                        .snippet(""))
                        .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_recycler));

            }

            vendorMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude()))
                    .title("current location"));

            vendorMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 10));


            progressBar.setVisibility(View.GONE);
            llTransparentBg.setVisibility(View.GONE);
        }
    }

    public void onAddNewItemClicked(View view) {

        Intent intent  = new Intent(this,AddItemsActivity.class);
        startActivity(intent);
    }
}
