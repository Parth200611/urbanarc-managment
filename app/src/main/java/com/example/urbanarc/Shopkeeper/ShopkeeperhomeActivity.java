package com.example.urbanarc.Shopkeeper;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.User.ContactuspageActivity;
import com.example.urbanarc.R;
import com.example.urbanarc.User.UserAboutUs;
import com.example.urbanarc.User.userhomeActivity;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shopkeeperhome_mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menumainmenushopkeeperhomemuprofil){
            Intent i = new Intent(ShopkeeperhomeActivity.this,ShopkeepermyprofilActivity.class);
            startActivity(i);

        }else if (item.getItemId() == R.id.menumainmenushopkeeperhomeaboutus){
            Intent i = new Intent(ShopkeeperhomeActivity.this, UserAboutUs.class);
            startActivity(i);

        }else if (item.getItemId() == R.id.menumainmenushopkeeperhomecontactus){
            Intent i = new Intent(ShopkeeperhomeActivity.this, ContactuspageActivity.class);
            startActivity(i);

        }
        return true;
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