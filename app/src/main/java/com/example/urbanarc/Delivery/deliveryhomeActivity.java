package com.example.urbanarc.Delivery;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.R;
import com.example.urbanarc.User.ContactuspageActivity;
import com.example.urbanarc.User.UserFeedBackActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class deliveryhomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryhome);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
        getWindow().setStatusBarColor(ContextCompat.getColor(deliveryhomeActivity.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(deliveryhomeActivity.this,R.color.white));
        bottomNavigationView = findViewById(R.id.bottomnevigationdeliveryhome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menudeliverybottomnevigationorders);

    }

    deliveryhomeFragment deliveryhomeFragment = new deliveryhomeFragment();
    deliveryorderhistoryFragment deliveryorderhistoryFragment = new deliveryorderhistoryFragment();
    deliveryordersFragment deliveryordersFragment = new deliveryordersFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menudeliverybottomnevigationorders){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutdeliveryhome, deliveryordersFragment).commit();
        }else if(item.getItemId()==R.id.menudeliverybottomnevigationorderhistory){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutdeliveryhome,deliveryorderhistoryFragment).commit();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deliveryhome_mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         if (item.getItemId() == R.id.menuDeliverymainmenuMyprofil){
            Intent i = new Intent(deliveryhomeActivity.this,deliveryMyprofilActivity.class);
            startActivity(i);

        }else if (item.getItemId() == R.id.menuDeliverymainmenuHelp){
             Intent i = new Intent(deliveryhomeActivity.this, ContactuspageActivity.class);
             startActivity(i);

        }
        return true;
    }
}