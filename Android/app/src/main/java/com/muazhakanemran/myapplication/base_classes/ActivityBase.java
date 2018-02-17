package com.muazhakanemran.myapplication.base_classes;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;

import com.muazhakanemran.myapplication.R;
import com.muazhakanemran.myapplication.bus_provider.BusProvider;
import com.muazhakanemran.myapplication.left_menu.LeftMenuFragment;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;

/**
 * Created by muazekici on 16.02.2018.
 */

public class ActivityBase extends AppCompatActivity {

    private Toolbar mToolbar;

    private View mViewContainer;

    private ViewGroup mRootContainer;

    private DrawerLayout mDrawerLayout;

    private LeftMenuFragment mLeftMenuFragment;

    private Bus mBus = BusProvider.getInstance();

    //if you want a custom layout override it on class extends this class
    public int getCustomLayout(){
        return -1;
    }

    //for toolbar layout, override it on class extends this class
    public int getToolbarLayout(){
        return -1;
    }

    //for content layout, override it on class extends this class
    public int getContentLayout(){
        return -1;
    }

    //for left menu layout
    public int getLeftMenuLayout(){
        return -1;
    }


    //to show whether left menu is used, override this method
    public boolean isUseLeftMenu(){
        return true;
    }

    //to show whether toolbar is used, override this method
    public boolean isUseToolbar(){
        return true;
    }

    public boolean isUseBackIcon() { return true;    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityLayouts();

    }

    private void setActivityLayouts(){
        if(getCustomLayout() != -1){

            setContentView(getCustomLayout());

        }else if(!isUseToolbar()){

            setContentView(R.layout.base_activity_no_toolbar_layout);

            setMainContainerLayout();

        }else{

            if(!isUseLeftMenu()){
                setContentView(R.layout.base_activity_no_leftmenu_layout);
            }else{
                setContentView(R.layout.base_activity_layout);
            }

            setToolbarLayout();
            setMainContainerLayout();

        }


    }

    private void setMainContainerLayout(){

        if(getContentLayout() == -1){
            throw new RuntimeException("Container layout is not defined.");
        }

        ViewStub mainContainer = (ViewStub) findViewById(R.id.vs_base_activity_content_viewstub);

        mainContainer.setLayoutResource(getContentLayout());

        mViewContainer = mainContainer.inflate();

        mRootContainer = (ViewGroup) findViewById(R.id.base_activity_root_container);


    }

    private void setToolbarLayout(){

        if(getToolbarLayout() == -1){
            throw new RuntimeException("Toolbar layout is not defined.");
        }

        ViewStub toolbar = (ViewStub) findViewById(R.id.vs_base_activity_toolbar_viewstub);

        toolbar.setLayoutResource(getToolbarLayout());

        toolbar.inflate();

        mToolbar = (Toolbar) findViewById(R.id.tb_base_activity_toolbar);

        setSupportActionBar(mToolbar);

        if(isUseLeftMenu()){

            setDrawerLayout();

            mToolbar.setNavigationIcon(R.drawable.ic_menu);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mDrawerLayout.openDrawer(Gravity.LEFT);
                        }
                    });
                }
            });

        }else if(isUseBackIcon()){
            showBackOnToolbar();
        }else{
            hideBackOnToolbar();
        }


    }

    private void setDrawerLayout(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_base_drawer_layout);

        setLeftMenu();

        setToogleListenerOnDrawer();


    }

    private void setToogleListenerOnDrawer(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_opened_text,R.string.drawer_closed_text){
            @Override
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                //action for left menu fully closed
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //action for left menu fully opened
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };

        mDrawerLayout.setDrawerListener(toggle);

        toggle.syncState();
    }

    private void setLeftMenu(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(getLeftMenuLayout() == -1){
            this.mLeftMenuFragment = LeftMenuFragment.newInstance(-1);
        }else{
            this.mLeftMenuFragment = LeftMenuFragment.newInstance(getLeftMenuLayout());

        }

        fragmentTransaction.add(R.id.ll_base_activity_leftmenu_container, mLeftMenuFragment);

        fragmentTransaction.commit();

    }

    private void setLeftMenuData(){
        //implement by calling public function in the fragment to set data and change data on left menu
    }

    //hide back icon on toolbar
    public void hideBackOnToolbar(){
        if(mToolbar == null){
            Log.e("Toolbar Err in Activity","Toolbar is not defined on the activity");
        }
        mToolbar.setNavigationIcon(null);
    }

    //show back icon on toolbar
    public void showBackOnToolbar(){
        if(mToolbar == null){
            Log.e("Toolbar Err in Activity","Toolbar is not defined on the activity");
        }

        mToolbar.setNavigationIcon(R.drawable.ic_login_back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }



    public void LockLeftMenu(){
        if(mDrawerLayout != null){
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public void UnlockLeftMenu(){
        if(mDrawerLayout != null){
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public void closeLeftMenu(){
        if(mDrawerLayout != null){
            mDrawerLayout.closeDrawers();
        }
    }

    public void hideStatusBar() {
        if(Build.VERSION.SDK_INT < 16 ) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(getResources().getColor(color));

            //TODO:change here to change color of status bar
        }
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    public void changeToolbarColor(int colorId){
        mToolbar.setBackgroundColor(getResources().getColor(colorId));
    }

    public Toolbar getmToolbar() {
        return mToolbar;
    }

    public View getmViewContainer() {
        return mViewContainer;
    }

    public ViewGroup getmRootContainer() {
        return mRootContainer;
    }

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    public LeftMenuFragment getmLeftMenuFragment() {
        return mLeftMenuFragment;
    }




    @Override
    protected void onResume() {
        super.onResume();
        mBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBus.unregister(this);
    }

    public Bus getBus(){
        return mBus;
    }


}

