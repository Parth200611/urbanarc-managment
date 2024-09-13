package com.example.urbanarc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class signupActivity extends AppCompatActivity {
    CardView cvcustomer,cvshopkeeper,cvother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setNavigationBarColor(ContextCompat.getColor(signupActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(signupActivity.this,R.color.signupbackgroundgreen));

        cvcustomer = findViewById(R.id.cvSignupcustomer);
        cvshopkeeper = findViewById(R.id.cvSignupshopkeeper);
        cvother = findViewById(R.id.cvSignupother);

        cvcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signupActivity.this, loginforcustomerActivity.class);
                startActivity(intent);
            }
        });

        cvshopkeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signupActivity.this, loginforshopkeeperActivity.class);
                startActivity(intent);
            }
        });

        cvother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signupActivity.this, loginforadminActivity.class);
                startActivity(intent);
            }
        });



    }
}