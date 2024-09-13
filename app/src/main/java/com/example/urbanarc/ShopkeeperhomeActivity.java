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

public class ShopkeeperhomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopkeeperhome);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
        getWindow().setStatusBarColor(ContextCompat.getColor(ShopkeeperhomeActivity.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(ShopkeeperhomeActivity.this,R.color.white));

        bottomNavigationView = findViewById(R.id.bottomnevigatiomshopkeeprhome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menuShopkeeperbottomnevigationhome);

    }
    shopkeeperhomeFragment shopkeeperhomeFragment = new shopkeeperhomeFragment();
    shopkeeperaddproductFragment shopkeeperaddproductFragment = new shopkeeperaddproductFragment();
    shopkeepercategoryFragment shopkeepercategoryFragment = new shopkeepercategoryFragment();
    shopkeepermyorderFragment shopkeepermyorderFragment = new shopkeepermyorderFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuShopkeeperbottomnevigationhome){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutshopkeeperhome,shopkeeperhomeFragment).commit();
        }else if (item.getItemId() == R.id.menuShopkeeperbottomnevigationadd){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutshopkeeperhome,shopkeeperaddproductFragment).commit();
        }else if (item.getItemId() == R.id.menuShopkeeperbottomnevigationmyorders){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutshopkeeperhome,shopkeepermyorderFragment).commit();
        }
        return true;
    }
}