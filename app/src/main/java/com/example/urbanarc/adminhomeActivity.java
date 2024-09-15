package com.example.urbanarc;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutadminhome,adminmanageuserFragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutadminhome,adminmanageordersFragment).commit();
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

        }else if (item.getItemId()==R.id.menuAdminmainmenuShopkeeper){

        }else if (item.getItemId()==R.id.menuAdminmainmenuDelivery){

        }else if (item.getItemId()==R.id.menuAdminmainmenufeedback){

        }else if (item.getItemId()==R.id.menuAdminmainmenuReport){

        }else if (item.getItemId()==R.id.menuAdminmainmenuMyprofil){
            Intent i = new Intent(adminhomeActivity.this,adminMyprofilActivity.class);
            startActivity(i);

        }
        return true;
    }
}