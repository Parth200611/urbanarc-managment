package com.example.urbanarc.Admin;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class adminhomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
        getWindow().setStatusBarColor(ContextCompat.getColor(adminhomeActivity.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(adminhomeActivity.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        bottomNavigationView = findViewById(R.id.bottomnevigationadminhome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menubottomnevigationadminHome);
    }

    adminhomeFragment adminhomeFragment = new adminhomeFragment();
    adminmanageordersFragment adminmanageordersFragment = new adminmanageordersFragment();
    adminmanageuserFragment adminmanageuserFragment = new adminmanageuserFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menubottomnevigationadminHome){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutadminhome,adminhomeFragment).commit();
        }else if (item.getItemId() == R.id.menubottomnevigationadminmanageorder){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutadminhome,adminmanageordersFragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutadminhome, adminmanageuserFragment).commit();
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.adminhome_mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdminmainmenuUsers){

            Intent i = new Intent(adminhomeActivity.this, AdmingetalluserdataActivity.class);
            startActivity(i);

        }else if (item.getItemId()==R.id.menuAdminmainmenuShopkeeper){

            Intent i = new Intent(adminhomeActivity.this,AdminShopkeeperlistActivity.class);
            startActivity(i);

        }else if (item.getItemId()==R.id.menuAdminmainmenuDelivery){
            Intent i = new Intent(adminhomeActivity.this, AdminDeliveryboydataActivity.class);
            startActivity(i);

        }else if (item.getItemId()==R.id.menuAdminmainmenufeedback){
            Intent i = new Intent(adminhomeActivity.this,AdminGetUserFeedBackActivity.class);
            startActivity(i);

        }else if (item.getItemId()==R.id.menuAdminmainmenuReport){

        }else if (item.getItemId()==R.id.menuAdminmainmenuMyprofil){
            Intent i = new Intent(adminhomeActivity.this,adminMyprofilActivity.class);
            startActivity(i);

        }
        return true;
    }
}