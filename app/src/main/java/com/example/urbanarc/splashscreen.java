package com.example.urbanarc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.Admin.adminhomeActivity;
import com.example.urbanarc.Delivery.deliveryhomeActivity;
import com.example.urbanarc.Shopkeeper.ShopkeeperhomeActivity;
import com.example.urbanarc.User.userMyprofilActivity;
import com.example.urbanarc.User.userhomeActivity;

public class splashscreen extends AppCompatActivity {
    ImageView ivlogo;
    TextView tvapptitle,tvappslogun;
  SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        getWindow().setNavigationBarColor(ContextCompat.getColor(splashscreen.this,R.color.splashbackgroundcolor));
        getWindow().setStatusBarColor(ContextCompat.getColor(splashscreen.this,R.color.splashbackgroundcolor));

        preferences = PreferenceManager.getDefaultSharedPreferences(splashscreen.this);
        editor=preferences.edit();


        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLoggedIn = preferences.getBoolean("isLogin", false);
                boolean isLoggedInshopkeeper = preferences.getBoolean("isLoginshopkeeper", false);
                boolean isLoggedInadmin = preferences.getBoolean("isLoginadmin", false);
                boolean isLoggedIndelivery = preferences.getBoolean("isLogindeliver", false);


                if (isLoggedIn) {
                    // User is already logged in, navigate to HomeActivity
                    Intent intent = new Intent(splashscreen.this, userhomeActivity.class);
                    startActivity(intent);
                } else if (isLoggedInshopkeeper) {
                    Intent i = new Intent(splashscreen.this, ShopkeeperhomeActivity.class);
                    startActivity(i);
                    finish();

                }else if (isLoggedInadmin) {
                    Intent i = new Intent(splashscreen.this, adminhomeActivity.class);
                    startActivity(i);
                    finish();

                }else if (isLoggedIndelivery) {
                    Intent i = new Intent(splashscreen.this, deliveryhomeActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    // User is not logged in, navigate to LoginActivity
                    Intent intent = new Intent(splashscreen.this, signupActivity.class);
                    startActivity(intent);
                    finish();
                }



            }
        },6000);
    }
}