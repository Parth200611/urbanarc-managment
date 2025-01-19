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
    String newname;
    String newmobile;
    String newaddress;
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
                if (etname.getText().toString().isEmpty()){
                    etname.setError("Enter Your name");
                } else  if (etmobileno.getText().toString().isEmpty()){
                    etmobileno.setError("Enter Your name");
                } else  if (etaddress.getText().toString().isEmpty()){
                    etaddress.setError("Enter Your name");
                }else {
                  String newname =etname.getText().toString();
                    String newmobile=etmobileno.getText().toString();
                    String newaddress=etaddress.getText().toString();

                    Intent i = new Intent(UseraddressModify.this,UserFinalBillsofa.class);
                    i.putExtra("username",strusername);
                    i.putExtra("image",strimage);
                    i.putExtra("productname",strproductname);
                    i.putExtra("price",strprice);
                    i.putExtra("offer",stroffer);
                    i.putExtra("discription",strdiscription);
                    i.putExtra("rating",strrating);
                    i.putExtra("shopname",strshopname);
                    i.putExtra("deliveryday",strdelivery);
                    i.putExtra("category",strcategory);
                    i.putExtra("productid",strproductid);
                    i.putExtra("userofname",newname);
                    i.putExtra("usermobileno",newmobile);
                    i.putExtra("useraddress",newaddress);
                    i.putExtra("lattitude",strlattitude);
                    i.putExtra("longitude",strlongitude);

                    startActivity(i);
                }

            }
        });
    }
}