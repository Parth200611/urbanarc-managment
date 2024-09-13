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

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class userhomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
        getWindow().setStatusBarColor(ContextCompat.getColor(userhomeActivity.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(userhomeActivity.this,R.color.white));

        bottomNavigationView = findViewById(R.id.bottomnevigatiomuserhome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menuUserhomebottomnavigationHome);

    }

    UserhomeFragment userhomeFragment=new UserhomeFragment();
    userExplorFragment userExplorFragment = new userExplorFragment();
    userMycartFragment userMycartFragment = new userMycartFragment();
    usercategoryFragment usercategoryFragment = new usercategoryFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuUserhomebottomnavigationHome){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,userhomeFragment).commit();
        }else if(item.getItemId()==R.id.menuUserhomebottomnavigationExplor){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,userExplorFragment).commit();
        } else if (item.getItemId()==R.id.menuUserhomebottomnavigationCategory){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,usercategoryFragment).commit();
        }else if(item.getItemId()==R.id.menuUserhomebottomnavigationCart){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,userMycartFragment).commit();
        }
        return true;
    }
}