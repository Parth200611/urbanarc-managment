package com.example.urbanarc.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.urbanarc.R;

public class Confirmorder extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    AppCompatButton btgotohome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmorder);
       getWindow().setStatusBarColor(ContextCompat.getColor(Confirmorder.this,R.color.green));
       getWindow().setNavigationBarColor(ContextCompat.getColor(Confirmorder.this,R.color.green));
       lottieAnimationView = findViewById(R.id.lotti);
       btgotohome = findViewById(R.id.btngotohome);
       btgotohome.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(Confirmorder.this,userhomeActivity.class);
               startActivity(i);
           }
       });
    }
}