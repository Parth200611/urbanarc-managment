package com.example.urbanarc.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(UserHomefragmentproductbed.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(UserHomefragmentproductbed.this,R.color.white));
        preferences = PreferenceManager.getDefaultSharedPreferences(UserHomefragmentproductbed.this);
        editor = preferences.edit();
        strUsername=preferences.getString("username",null);

        strid=getIntent().getStringExtra("id");

        tvproductname = findViewById(R.id.tvUserHomepageBedProductName);
        tvproductdiscription=findViewById(R.id.tvUserHomepageProductBedDiscription);
        tvproductprice=findViewById(R.id.tvUserHomepageProductBedPrice);
        tvproductrating=findViewById(R.id.tvUserHomepageProductBedRating);
        tvproductoffer=findViewById(R.id.tvUserHomepageProductBedoffer);
        ivproductimage=findViewById(R.id.ivUserHomepageProductBedDetailsSofaimage);
        tvshopname = findViewById(R.id.tvUserHomepageProductBedshopname);
        tvdeliveryday = findViewById(R.id.tvUserHomepageProductBeddelivery);
        ivAddtoFav = findViewById(R.id.ivbedheartIcon);
        btnaddtocart =  findViewById(R.id.btnUserHomepageProductBedAddtoCart);
        btnBuynow=findViewById(R.id.btnUserHomepageProductBedBuyNow);

        btnaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddtoCart();
            }
        });
        ivAddtoFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHeart();
            }
        });
        btnBuynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHomefragmentproductbed.this,UseraddressfromSofa.class);
                i.putExtra("username", strUsername);
                i.putExtra("image", strimage);
                i.putExtra("productname", strproductname);
                i.putExtra("price", strprice);
                i.putExtra("offer", stroffer);
                i.putExtra("dicrption", strdiscription);
                i.putExtra("rating", strrating);
                i.putExtra("diliveryday", strdelivery);
                i.putExtra("shopname", strshopname);
                i.putExtra("category", strcategory);
                i.putExtra("productid", strid);
                startActivity(i);
            }
        });

        getproductdetails(strid);

    }


    private void getproductdetails(String strid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("id",strid);



        client.post(urls.Userhomegetproductdetails,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("Productdata");
                    for (int  i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        strpid = jsonObject.getString("id");
                        strimage = jsonObject.getString("image");
                        strcategory = jsonObject.getString("category");
                        strproductname = jsonObject.getString("productname");
                        strprice = jsonObject.getString("price");
                        stroffer = jsonObject.getString("offer");
                        strdiscription = jsonObject.getString("discription");
                        strrating = jsonObject.getString("rating");
                        strshopname = jsonObject.getString("shopname");
                        strdelivery = jsonObject.getString("deliverydays");

                        tvproductname.setText(strproductname);
                        tvproductdiscription.setText(strdiscription);
                        tvproductprice.setText(strprice);
                        tvproductoffer.setText(stroffer);
                        tvproductrating.setText(strrating);
                        tvshopname.setText(strshopname);
                        tvdeliveryday.setText(strdelivery);
                        editor.putString("userhomesofaimagebed",strimage).commit();
                        editor.putString("userhomesofacategorybed",strcategory).commit();
                        editor.putString("userhomesofaproductnamebed",strproductname).commit();
                        editor.putString("userhomesofapricebed",strprice).commit();
                        editor.putString("userhomesofaofferbed",stroffer).commit();
                        editor.putString("userhomesofadiscriptionbed",strdiscription).commit();
                        editor.putString("userhomesofaratingbed",strrating).commit();
                        editor.putString("userhomesofashopnamebed",strshopname).commit();
                        editor.putString("userhomesofadeliverybed",strdelivery).commit();
                        Glide.with(UserHomefragmentproductbed.this)
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
                Toast.makeText(UserHomefragmentproductbed.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleHeart() {
        if (isHeartFilled) {
            // Change back to outline heart and update state
            ivAddtoFav.setImageResource(R.drawable.heratoutlin);
            isHeartFilled = false;
        } else {
            // Change to filled heart, call the server method, and update state
            ivAddtoFav.setImageResource(R.drawable.fillheart);
            isHeartFilled = true;
            AddToFavList();  // Call the backend method when heart is filled
        }
    }




    private void AddToFavList() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strUsername);
        params.put("shopname",strshopname);
        params.put("image",strimage);
        params.put("category",strcategory);
        params.put("productname",strproductname);
        params.put("price",strprice);
        params.put("offer",stroffer);
        params.put("discription",strdiscription);
        params.put("rating",strrating);
        params.put("deliveryday",strdelivery);
        params.put("productid",strid);

        client.post(urls.Userhomepagefavlistproduct,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(UserHomefragmentproductbed.this, "Added To Wishlist", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(UserHomefragmentproductbed.this, "Product Already exits", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(UserHomefragmentproductbed.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void AddtoCart() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strUsername);
        params.put("shopname",strshopname);
        params.put("image",strimage);
        params.put("category",strcategory);
        params.put("productname",strproductname);
        params.put("price",strprice);
        params.put("offer",stroffer);
        params.put("discription",strdiscription);
        params.put("rating",strrating);
        params.put("deliveryday",strdelivery);
        params.put("productid",strpid);

        client.post(urls.addtocart,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(UserHomefragmentproductbed.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(UserHomefragmentproductbed.this, "Server Problem", Toast.LENGTH_SHORT).show();
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