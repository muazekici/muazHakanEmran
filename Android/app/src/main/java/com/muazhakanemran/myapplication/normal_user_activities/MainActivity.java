package com.muazhakanemran.myapplication.normal_user_activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.preference.PreferenceManager;
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
import com.muazhakanemran.myapplication.courier_user_activities.CourierMainActivity;
import com.muazhakanemran.myapplication.R;
import com.muazhakanemran.myapplication.base_classes.ActivityBase;
import com.muazhakanemran.myapplication.events.GetNearVendingMachinesEvent;
import com.muazhakanemran.myapplication.events.VendorListResponseEvent;
import com.muazhakanemran.myapplication.left_menu.LeftMenuFragment;
import com.muazhakanemran.myapplication.models.GetNearVendorsRequest;
import com.muazhakanemran.myapplication.models.Vendor;
import com.muazhakanemran.myapplication.models.VendorList;
import com.squareup.otto.Subscribe;

public class MainActivity extends ActivityBase implements OnMapReadyCallback,LeftMenuFragment.onLeftMenuItemClicked {


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
    public boolean isUseLeftMenu() { return true;    }

    @Override
    public boolean isUseBackIcon() { return false;    }

    @Override
    public boolean isUseToolbar() {
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        initComponents();
        getmLeftMenuFragment().setItemClickListener(this);
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
                req.setDist(100000);
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
        View view = getmToolbar().findViewById(R.id.iv_toolbar_right_item);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeLeftMenu();
                Intent intent  = new Intent(MainActivity.this,CurrentDepositActivity.class);
                startActivity(intent);
            }
        });

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
/*
            List<LatLng> positionList = new ArrayList<LatLng>();
*/
            for(Vendor v : mVendorList.getVendors()){
                vendorMap.addMarker(new MarkerOptions()
                        .position(new LatLng(v.getLat(),v.getLng()))
                        .title(v.getName())
                        .snippet(""))
                        .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_recycler));

/*
                        positionList.add(new LatLng(v.getLat(),v.getLng()));
*/

            }

          /*  PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            options.addAll(positionList);
            Polyline polyline = vendorMap.addPolyline(options);*/


            vendorMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude()))
                    .title("current location"));

            vendorMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 13));


            progressBar.setVisibility(View.GONE);
            llTransparentBg.setVisibility(View.GONE);
        }
    }

    public void onAddNewItemClicked(View view) {

        Intent intent  = new Intent(this,AddItemsActivity.class);
        startActivity(intent);
    }


    @Override
    public void onItemClicked(int id) {
        switch (id){
            case R.id.left_menu_item1:
                closeLeftMenu();
                Intent intent  = new Intent(this,AddItemsActivity.class);
                startActivity(intent);
                break;
            case R.id.left_menu_item2:
                closeLeftMenu();
                Intent intent1  = new Intent(this,CurrentDepositActivity.class);
                startActivity(intent1);
                break;
            case R.id.left_menu_item3:
                closeLeftMenu();
                Intent intent2  = new Intent(this,DeliverItemsActivity.class);
                startActivity(intent2);
                break;
            case R.id.left_menu_item4:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("userRole",1);
                editor.commit();
                closeLeftMenu();
                Intent intent3  = new Intent(this,CourierMainActivity.class);
                startActivity(intent3);

        }
    }
}
