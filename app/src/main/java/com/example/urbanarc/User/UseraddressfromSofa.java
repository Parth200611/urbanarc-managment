package com.example.urbanarc.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UseraddressfromSofa extends AppCompatActivity {
     TextView tvcancleorder,tvnameofuser,tvuseraddress,tvmobileno;
     AppCompatButton btndeliveraddress,btnmodifyaddress;
     String strusername,strimage,strproductname,strprice,stroffer,strdiscription,strrating,strdelivery,strshopname,strcategory,strproductid;
     String strgetNameofuser,strgetMobilenoofuser,strgetUseraddress;
     String strlattitude,strlongitude,straddress;
     SharedPreferences preferences;
     SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_useraddressfrom_sofa);
        getWindow().setNavigationBarColor(ContextCompat.getColor(UseraddressfromSofa.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(UseraddressfromSofa.this,R.color.green));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        preferences= PreferenceManager.getDefaultSharedPreferences(UseraddressfromSofa.this);
        editor = preferences.edit();


        strgetNameofuser = preferences.getString("nameofuser","");


        strusername=getIntent().getStringExtra("username");
        strimage=getIntent().getStringExtra("image");
        strproductname=getIntent().getStringExtra("productname");
        strprice=getIntent().getStringExtra("price");
        stroffer=getIntent().getStringExtra("offer");
        strdiscription=getIntent().getStringExtra("dicrption");
        strrating=getIntent().getStringExtra("rating");
        strdelivery=getIntent().getStringExtra("diliveryday");
        strshopname=getIntent().getStringExtra("shopname");
        strcategory=getIntent().getStringExtra("category");
        strproductid=getIntent().getStringExtra("productid");

        tvcancleorder = findViewById(R.id.Useraddresscancleorder);
        tvnameofuser = findViewById(R.id.Useraddressusername);
        tvuseraddress = findViewById(R.id.Useraddressuseraddress);
        tvmobileno = findViewById(R.id.Useraddressusermobileno);
        btnmodifyaddress = findViewById(R.id.btnUseraddressModifiaddress);
        btndeliveraddress=findViewById(R.id.btnUseraddressselectaddress);

        btndeliveraddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UseraddressfromSofa.this,UserFinalBillsofa.class);
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
                i.putExtra("userofname",strgetNameofuser);
                i.putExtra("usermobileno",strgetMobilenoofuser);
                i.putExtra("useraddress",straddress);
                i.putExtra("lattitude",strlattitude);
                i.putExtra("longitude",strlongitude);

                startActivity(i);

            }
        });
        tvcancleorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UseraddressfromSofa.this,userhomeActivity.class);
                startActivity(i);
            }
        });
         btnmodifyaddress.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(UseraddressfromSofa.this,UseraddressModify.class);
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
                 i.putExtra("userofname",strgetNameofuser);
                 i.putExtra("usermobileno",strgetMobilenoofuser);
                 i.putExtra("useraddress",straddress);
                 startActivity(i);
             }
         });

        tvnameofuser.setText(strgetNameofuser);


        getUserAddrress(strusername);





    }

    private void getUserAddrress(String strusername) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username",strusername);
        client.post(urls.getuseraddress,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getuseraddress");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        strlattitude = jsonObject.getString("lattitude");
                        strlongitude = jsonObject.getString("longitude");
                        straddress = jsonObject.getString("address");
                        strgetMobilenoofuser = jsonObject.getString("mobileno");

                        tvuseraddress.setText(straddress);
                        tvmobileno.setText(strgetMobilenoofuser);

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}