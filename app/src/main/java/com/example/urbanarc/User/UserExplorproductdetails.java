package com.example.urbanarc.User;

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

public class UserExplorproductdetails extends AppCompatActivity {
    TextView tvproductname,tvdiscription,tvprice,tvrating,tvoffer,tvdelivery,tvshopname;
    ImageView ivproductimage,ivAddtoFav;
    AppCompatButton btnaddtocart,btnbuynow;
    String strid,strUsername;
    String strproductname,strdiscription,strprice,strrating,stroffer,strshopnem,strdelivery,strid1,strcategory,strimage;
    SharedPreferences preferences;
    private boolean isHeartFilled = false;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_explorproductdetails);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.green));
        preferences = PreferenceManager.getDefaultSharedPreferences(UserExplorproductdetails.this);
        editor = preferences.edit();
        strUsername=preferences.getString("username",null);

        strid=getIntent().getStringExtra("id");

        tvproductname = findViewById(R.id.tvUserHomepageProductName);
        tvdiscription=findViewById(R.id.tvUserHomepageProductDiscription);
        tvprice=findViewById(R.id.tvUserHomepageProductPrice);
        tvrating=findViewById(R.id.tvUserHomepageProductRating);
        tvoffer=findViewById(R.id.tvUserHomepageProductoffer);
        ivproductimage=findViewById(R.id.ivUserHomepageProductDetailsSofaimage);
        tvshopname = findViewById(R.id.tvUserHomepageProductshopname);
        tvdelivery = findViewById(R.id.tvUserHomepageProductdelivery);
        ivAddtoFav = findViewById(R.id.heartIcon);
        btnaddtocart = findViewById(R.id.btnUserHomepageProductAddtoCart);
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


        getproductdetails(strid);

    }




    private void getproductdetails(String strid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("id",strid);

        client.post(urls.UserExplorProductdetails,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray  = response.getJSONArray("getproductdetails");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        strid1 = jsonObject.getString("id");
                        strproductname = jsonObject.getString("productname");
                        strshopnem = jsonObject.getString("shopname");
                        strcategory = jsonObject.getString("category");
                        strprice = jsonObject.getString("price");
                        stroffer = jsonObject.getString("offer");
                        strdiscription = jsonObject.getString("discription");
                        strrating = jsonObject.getString("rating");
                        strdelivery = jsonObject.getString("delivery");
                        strimage = jsonObject.getString("image");


                        tvproductname.setText(strproductname);
                        tvdiscription.setText(strdiscription);
                        tvprice.setText(strprice);
                        tvoffer.setText(stroffer);
                        tvrating.setText(strrating);
                        tvshopname.setText(strshopnem);
                        tvdelivery.setText(strdelivery);

                        Glide.with(UserExplorproductdetails.this)
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
                Toast.makeText(UserExplorproductdetails.this, "server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void AddtoCart() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strUsername);
        params.put("shopname",strshopnem);
        params.put("image",strimage);
        params.put("category",strcategory);
        params.put("productname",strproductname);
        params.put("price",strprice);
        params.put("offer",stroffer);
        params.put("discription",strdiscription);
        params.put("rating",strrating);
        params.put("deliveryday",strdelivery);
        params.put("productid",strid1);

        client.post(urls.addtocart,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(UserExplorproductdetails.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(UserExplorproductdetails.this, "Server Problem", Toast.LENGTH_SHORT).show();
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
        params.put("shopname",strshopnem);
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
                        Toast.makeText(UserExplorproductdetails.this, "Added To Wishlist", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(UserExplorproductdetails.this, "error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(UserExplorproductdetails.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}