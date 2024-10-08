package com.example.urbanarc.User;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.userhome_mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menumainmenuuserhomenewoffers){

        }else if (item.getItemId()==R.id.menumainmenuuserhomemyorders){

        } else if (item.getItemId() == R.id.menumainmenuuserhomewishlist) {

        } else if (item.getItemId() == R.id.menumainmenuuserhomemyprofil) {
            Intent i = new Intent(userhomeActivity.this,userMyprofilActivity.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.menumainmenuuserhomeaboutus) {

        } else if (item.getItemId() == R.id.menumainmenuuserhomehelp) {
            Intent i = new Intent(userhomeActivity.this, ContactuspageActivity.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.menumainmenuuserhomefeedback) {
            Intent i = new Intent(userhomeActivity.this, UserFeedBackActivity.class);
            startActivity(i);

        }

        return true;
    }
}