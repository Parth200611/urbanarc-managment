package com.example.urbanarc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.SplashScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splashscreen extends AppCompatActivity {
    ImageView ivlogo;
    TextView tvapptitle,tvappslogun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        getWindow().setNavigationBarColor(ContextCompat.getColor(splashscreen.this,R.color.splashbackgroundcolor));
        getWindow().setStatusBarColor(ContextCompat.getColor(splashscreen.this,R.color.splashbackgroundcolor));

        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(splashscreen.this,welcomescreenActivity.class);
                startActivity(intent);
            }
        },6000);
    }
}