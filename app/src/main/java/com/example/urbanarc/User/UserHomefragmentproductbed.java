package com.example.urbanarc.User;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanarc.R;

public class UserHomefragmentproductbed extends AppCompatActivity {
    TextView tvproductname,tvproductdiscription,tvproductprice,tvproductoffer,tvproductrating,tvshopname,tvdeliveryday;
    ImageView ivproductimage,ivAddtoFav;
    AppCompatButton btnBuynow,btnaddtocart;
    ProgressDialog progressDialog;
    String strid,strUsername;
    private boolean isHeartFilled = false;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String shopname,image,category,productname,price,offer,discription,rating,deliverybay;
    String strpid,strimage,strcategory,strproductname,strprice,stroffer,strdiscription,strrating,strshopname,strdelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homefragmentproductbed);
        getWindow().setStatusBarColor(ContextCompat.getColor(UserHomefragmentproductbed.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(UserHomefragmentproductbed.this,R.color.white));
        preferences = PreferenceManager.getDefaultSharedPreferences(UserHomefragmentproductbed.this);
        editor = preferences.edit();

    }
}