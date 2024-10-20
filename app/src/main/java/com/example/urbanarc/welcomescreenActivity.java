package com.example.urbanarc;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.urbanarc.comman.NetworkChangeListner;

import java.util.ArrayList;

public class welcomescreenActivity extends AppCompatActivity {
    ImageSlider imageSlider;
    TextView tvwelcomeNext;
    NetworkChangeListner networkChangeListner = new NetworkChangeListner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);
        getWindow().setNavigationBarColor(ContextCompat.getColor(welcomescreenActivity.this,R.color.lightgreen));
        getWindow().setStatusBarColor(ContextCompat.getColor(welcomescreenActivity.this,R.color.splashbackgroundcolor));
        imageSlider = findViewById(R.id.imagesliderwelcome);
        tvwelcomeNext = findViewById(R.id.tvwelcomeNext);

        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();
        slideModelArrayList.add(new SlideModel(R.drawable.welcomeimage2, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.welcomeimage8, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.welcomeimage5, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.welcomeimage4, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModelArrayList);
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_IN);

        tvwelcomeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welcomescreenActivity.this, signupActivity.class);
                startActivity(intent);
            }
        });






    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListner);
    }
}