package com.example.dell.kindergarten;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mxn.soul.flowingdrawer_core.FlowingView;
import com.mxn.soul.flowingdrawer_core.LeftDrawerLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener
{
//TextView content;
com.mxn.soul.flowingdrawer_core.LeftDrawerLayout  drawers;
    SchoolDetailFragment schoolDetailFragment;
    LeftDrawerLayout drawer;
    HomeFragment homeFragment;
    School.AboutusFragment aboutusFragment;
    ContactUs contactusFragment;
    LocationFragment location;
    AppDetail appDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appDetail=new AppDetail();
    contactusFragment=new ContactUs();
        //content = (TextView) findViewById(R.id.textSchool);
        AdView ad= (AdView) findViewById(R.id.PreID);
        AdRequest request= new AdRequest.Builder()
                .addTestDevice("172330D5F4EF0BD4A348FA76873071DB")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        ad.loadAd(request);

        homeFragment=new HomeFragment();
        schoolDetailFragment=new SchoolDetailFragment();
        aboutusFragment=new School.AboutusFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frag_container,homeFragment).commit();

        drawer = (LeftDrawerLayout) findViewById(R.id.drawer_layout);
        MyMenuFragment mMenuFragment = new MyMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        FlowingView mFlowingView = (FlowingView) findViewById(R.id.sv);
        drawer.setFluidView(mFlowingView);
        drawer.setMenuFragment(mMenuFragment);

    }

    @Override
    public void onBackPressed() {
  drawers = (LeftDrawerLayout) findViewById(R.id.drawer_layout);
        if (drawers.isShownMenu()) {
            drawers.closeDrawer();
        }
else {
           super.onBackPressed();
        }
    }



    public void onRatingClick(View view) {
        homeFragment.onRatingClick();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,homeFragment).addToBackStack(null).commit();
    drawer.closeDrawer();
    }

    public void onLocationClick(View view) {
        location=new LocationFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,location).addToBackStack(null).commit();
        drawer.closeDrawer();

    }

    public void onSearchClick(View view) {
    homeFragment.onSearchClick();
    }



    public void onAboutUsClick(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,aboutusFragment).addToBackStack(null).commit();
        drawer.closeDrawer();
    }

    public void onContactusClick(View view) {

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,contactusFragment).addToBackStack(null).commit();
        drawer.closeDrawer();
    }

    public void onMenuClick(View view) {
        drawer.toggle();
    }

    }

