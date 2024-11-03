package com.example.urbanarc.User;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanarc.R;

public class UserAboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_about_us);
        getWindow().setStatusBarColor(ContextCompat.getColor(UserAboutUs.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(UserAboutUs.this,R.color.white));

    }
}