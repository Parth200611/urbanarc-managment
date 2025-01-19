package com.example.urbanarc.User;

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

public class userwishlistproductdetails extends AppCompatActivity {

    TextView tvproductname,tvdiscription,tvprice,tvrating,tvoffer,tvdelivery,tvshopname;
    ImageView ivproductimage,ivAddtoFav;
    AppCompatButton btnaddtocart,btnbuynow;
    String strid,strUsername;
    String strproductname,strdiscription,strprice,strrating,stroffer,strshopname,strdelivery,strid1,strcategory,strimage;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    boolean isRed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userwishlistproductdetails);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.green));
        preferences = PreferenceManager.getDefaultSharedPreferences(userwishlistproductdetails.this);
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
        btnbuynow=findViewById(R.id.btnUserHomepageProductBuyNow);

        btnbuynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userwishlistproductdetails.this,UseraddressfromSofa.class);
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

        btnaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddtoCart();
            }
        });


        ivAddtoFav.setOnClickListener(v -> {
            if (isRed) {
                // Change to white and call dataDelete
                ivAddtoFav.setImageResource(R.drawable.heratoutlin);
                Removefromwishlist();
            } else {
                // Change back to red and call dataAddAgain
                ivAddtoFav.setImageResource(R.drawable.fillheart);
                AddTowishlistagain();
            }
            // Toggle the state
            isRed = !isRed;
        });
        
        getProductdetails(strid);

    }

    private void getProductdetails(String strid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id",strid);

        client.post(urls.userWishlistdetails,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray  = response.getJSONArray("getproductdetails");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        strid1 = jsonObject.getString("id");
                        strproductname = jsonObject.getString("productname");
                        strshopname = jsonObject.getString("shopname");
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
                        tvshopname.setText(strshopname);
                        tvdelivery.setText(strdelivery);

                        Glide.with(userwishlistproductdetails.this)
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
                Toast.makeText(userwishlistproductdetails.this, "server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AddTowishlistagain() {
    }

    private void Removefromwishlist() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("id",strid);
        client.post(urls.wishlistdelete,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status  = response.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(userwishlistproductdetails.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(userwishlistproductdetails.this,UserWishlist.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(userwishlistproductdetails.this, "Error", Toast.LENGTH_SHORT).show();
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
        params.put("productid",strid1);

        client.post(urls.addtocart,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(userwishlistproductdetails.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(userwishlistproductdetails.this, "Server Problem", Toast.LENGTH_SHORT).show();
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