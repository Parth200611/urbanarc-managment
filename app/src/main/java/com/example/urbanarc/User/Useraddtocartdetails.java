package com.example.urbanarc.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.PixelCopy;
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

public class Useraddtocartdetails extends AppCompatActivity {
    TextView tvproductname,tvdiscription,tvprice,tvrating,tvoffer,tvdelivery,tvshopname;
    ImageView ivproductimage,ivAddtoFav;
    AppCompatButton btnbuynow,btnremovefromcart;
    String strid,strUsername;
    String strproductname,strdiscription,strprice,strrating,stroffer,strshopname,strdelivery,strproductid,strcategory,strimage;
    SharedPreferences preferences;
    String day="3-4 days";
    SharedPreferences.Editor editor;
    boolean isHeartFilled=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraddtocartdetails);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.green));
        preferences = PreferenceManager.getDefaultSharedPreferences(Useraddtocartdetails.this);
        editor = preferences.edit();
        strUsername = preferences.getString("username",null);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        strid = getIntent().getStringExtra("id");

        tvproductname = findViewById(R.id.tvUserHomepageProductName);
        tvdiscription=findViewById(R.id.tvUserHomepageProductDiscription);
        tvprice=findViewById(R.id.tvUserHomepageProductPrice);
        tvrating=findViewById(R.id.tvUserHomepageProductRating);
        tvoffer=findViewById(R.id.tvUserHomepageProductoffer);
        ivproductimage=findViewById(R.id.ivUserHomepageProductDetailsSofaimage);
        tvshopname = findViewById(R.id.tvUserHomepageProductshopname);
        tvdelivery = findViewById(R.id.tvUserHomepageProductdelivery);
        ivAddtoFav = findViewById(R.id.heartIcon);
        btnremovefromcart=findViewById(R.id.btnUserHomepageProductRemovefromCart);
        btnbuynow=findViewById(R.id.btnUserHomepageProductBuyNow);

        btnbuynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Useraddtocartdetails.this,UseraddressfromSofa.class);
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

        btnremovefromcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Removefromcart(strid);
            }
        });
        ivAddtoFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHeart();
            }
        });

        getData(strid);

    }

    private void Removefromcart(String strid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id",strid);
        client.post(urls.Userremovefromcart,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(Useraddtocartdetails.this, "Removed from My Cart", Toast.LENGTH_SHORT).show();

                    } else{
                        Toast.makeText(Useraddtocartdetails.this, "Can't Remove", Toast.LENGTH_SHORT).show();
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


    private void getData(String strid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("id",strid);
        client.post(urls.Useraddtocartdetails,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getaddtocartdatadetails");
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        strshopname=jsonObject1.getString("shopname");
                        strimage=jsonObject1.getString("image");
                        strcategory=jsonObject1.getString("category");
                        strproductname=jsonObject1.getString("productname");
                        strprice=jsonObject1.getString("price");
                        stroffer=jsonObject1.getString("offer");
                        strdiscription=jsonObject1.getString("discription");
                        strrating=jsonObject1.getString("rating");

                        strproductid=jsonObject1.getString("productid");

                        tvproductname.setText(strproductname);
                        tvdiscription.setText(strdiscription);
                        tvprice.setText(strprice);
                        tvrating.setText(strrating);
                        tvshopname.setText(strshopname);
                        tvoffer.setText(stroffer);
                        strdelivery=day;
                        tvdelivery.setText(strdelivery);

                        Glide.with(Useraddtocartdetails.this)
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
        params.put("productid",strproductid);

        client.post(urls.Userhomepagefavlistproduct,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(Useraddtocartdetails.this, "Added To Wishlist", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Useraddtocartdetails.this, "Product Already exits", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Useraddtocartdetails.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}