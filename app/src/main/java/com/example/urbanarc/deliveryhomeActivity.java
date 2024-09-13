package com.example.urbanarc;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        bottomNavigationView.setSelectedItemId(R.id.menudeliverybottomnevigationhome);

    }

    deliveryhomeFragment deliveryhomeFragment = new deliveryhomeFragment();
    deliveryorderhistoryFragment deliveryorderhistoryFragment = new deliveryorderhistoryFragment();
    deliveryordersFragment deliveryordersFragment = new deliveryordersFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menudeliverybottomnevigationhome){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutdeliveryhome, deliveryhomeFragment).commit();
        }else if(item.getItemId()==R.id.menudeliverybottomnevigationorders){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutdeliveryhome, deliveryordersFragment).commit();
        }else if(item.getItemId()==R.id.menudeliverybottomnevigationorderhistory){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutdeliveryhome,deliveryorderhistoryFragment).commit();
        }
        return true;
    }
}