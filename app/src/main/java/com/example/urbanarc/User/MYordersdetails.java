package com.example.urbanarc.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MYordersdetails extends AppCompatActivity {

    TextView tvproductname,tvdiscription,tvprice,tvshopname,tvoffer,tvdelivery,tvpaymentmode,tvrating;
    ImageView ivproductimage;
    String productid;
    String strid,strUsername;
    AppCompatButton btnrtemove;
    String strproductname,strdiscription,strprice,strrating,stroffer,strshopname,strdelivery,strid1,strcategory,strimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_myordersdetails);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.green));
        productid=getIntent().getStringExtra("productid");

        tvproductname = findViewById(R.id.tvUserHomepageProductName);
        tvdiscription=findViewById(R.id.tvUserHomepageProductDiscription);
        tvprice=findViewById(R.id.tvUserHomepageProductPrice);

        tvoffer=findViewById(R.id.tvUserHomepageProductoffer);
        ivproductimage=findViewById(R.id.ivUserHomepageProductDetailsSofaimage);
        tvshopname = findViewById(R.id.tvUserHomepageProductshopname);
        tvdelivery = findViewById(R.id.tvUserHomepageProductdelivery);
        tvpaymentmode=findViewById(R.id.tvPaymentMode);
        btnrtemove=findViewById(R.id.btnremovefromlist);

        btnrtemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Removefromcart(productid);
            }
        });


        getProductdetails();


    }

    private void getProductdetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("productid",productid);

        client.post(urls.UserMyOrdersDEtails,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray  = response.getJSONArray("getallordersdetails");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        strid1 = jsonObject.getString("id");
                        String Username = jsonObject.getString("username");
                        String name = jsonObject.getString("name");
                        String mobile = jsonObject.getString("mobileno");
                        String longitude = jsonObject.getString("lattitude");
                        String lattitude = jsonObject.getString("longitude");
                        String add = jsonObject.getString("address");
                        strimage = jsonObject.getString("image");
                        strproductname = jsonObject.getString("productname");
                        strprice = jsonObject.getString("price");
                        stroffer = jsonObject.getString("offer");
                        strdiscription = jsonObject.getString("discription");
                        strdelivery = jsonObject.getString("delieryday");
                        strshopname = jsonObject.getString("shopname");
                        strcategory = jsonObject.getString("category");
                        String proid = jsonObject.getString("productid");
                        String strpay = jsonObject.getString("payment");


                        tvproductname.setText(strproductname);
                        tvdiscription.setText(strdiscription);
                        tvprice.setText(strprice);
                        tvoffer.setText(stroffer);
//                        tvrating.setText(strrating);
                        tvshopname.setText(strshopname);
                        tvdelivery.setText(strdelivery);
                        tvpaymentmode.setText(strpay);

                        Glide.with(MYordersdetails.this)
                                .load(urls.address + "images/"+strimage)
                                .skipMemoryCache(true)
                                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                                .into(ivproductimage);



                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(MYordersdetails.this, "server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void Removefromcart(String strid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("productid",strid);
        client.post(urls.removeorder,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(MYordersdetails.this, "Order Cancle", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MYordersdetails.this,UserOrders.class);
                        startActivity(i);

                    } else{
                        Toast.makeText(MYordersdetails.this, "Can't Remove", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, userhomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Close the current activity
    }

}