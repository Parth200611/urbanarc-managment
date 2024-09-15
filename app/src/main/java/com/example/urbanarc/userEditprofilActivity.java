package com.example.urbanarc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class userEditprofilActivity extends AppCompatActivity {

    CardView cvemail,cvpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setNavigationBarColor(ContextCompat.getColor(userEditprofilActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(userEditprofilActivity.this,R.color.green));
        setContentView(R.layout.activity_user_editprofil);
        cvemail = findViewById(R.id.cvUserEditprofilEditemail);
        cvpassword = findViewById(R.id.cvUserEditprofilEditpassword);

        cvemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(userEditprofilActivity.this,UsercheckusernamefoeemailupdateActivity.class);
                startActivity(i);
            }
        });

        cvpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(userEditprofilActivity.this,usernumbercheckforforgottenpassworrd.class);
                startActivity(i);

            }
        });

    }
}