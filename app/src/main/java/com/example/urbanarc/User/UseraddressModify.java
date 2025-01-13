package com.example.urbanarc.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanarc.R;

public class UseraddressModify extends AppCompatActivity {
    EditText etname,etmobileno,etaddress;
    String strusername,strimage,strproductname,strprice,stroffer,strdiscription,strrating,strdelivery,strshopname,strcategory,strproductid;
    String strgetNameofuser,strgetMobilenoofuser,strgetUseraddress;
    String strlattitude,strlongitude,straddress;
    AppCompatButton btnselectaddress;
    private static final int REQUEST_CODE_MAP = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraddress_modify);
        getWindow().setStatusBarColor(ContextCompat.getColor(UseraddressModify.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(UseraddressModify.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        strusername=getIntent().getStringExtra("username");
        strimage=getIntent().getStringExtra("image");
        strproductname=getIntent().getStringExtra("productname");
        strprice=getIntent().getStringExtra("price");
        stroffer=getIntent().getStringExtra("offer");
        strdiscription=getIntent().getStringExtra("discription");
        strrating=getIntent().getStringExtra("rating");
        strshopname=getIntent().getStringExtra("shopname");
        strdelivery=getIntent().getStringExtra("deliveryday");
        strcategory=getIntent().getStringExtra("category");
        strproductid=getIntent().getStringExtra("productid");
        strgetNameofuser=getIntent().getStringExtra("userofname");
        strgetMobilenoofuser=getIntent().getStringExtra("usermobileno");
        strgetUseraddress=getIntent().getStringExtra("useraddress");

        etname=findViewById(R.id.UserModifyNamedelivery);
        etmobileno=findViewById(R.id.UserModifyMobilenodelivery);
        etaddress=findViewById(R.id.UserModifyAddressdelivery);

        btnselectaddress=findViewById(R.id.btnUserModifyaddressselectlocation);

        etname.setText(strgetNameofuser);
        etmobileno.setText(strgetMobilenoofuser);
        etaddress.setText(strgetUseraddress);

        btnselectaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }
}