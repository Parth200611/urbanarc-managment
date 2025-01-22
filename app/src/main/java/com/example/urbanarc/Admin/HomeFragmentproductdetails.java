package com.example.urbanarc.Admin;

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
import com.example.urbanarc.User.UserExplorproductdetails;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class HomeFragmentproductdetails extends AppCompatActivity {
    TextView tvproductname,tvdiscription,tvprice,tvrating,tvoffer,tvdelivery,tvshopname;
    ImageView ivproductimage,ivAddtoFav;
    AppCompatButton btnaddtocart,btncancleproduct;
    String strid,strUsername;
    String strproductname,strdiscription,strprice,strrating,stroffer,strshopname,strdelivery,strid1,strcategory,strimage;
    SharedPreferences preferences;
    private boolean isHeartFilled = false;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_fragmentproductdetails);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.green));
        preferences = PreferenceManager.getDefaultSharedPreferences(HomeFragmentproductdetails.this);
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
        btncancleproduct=findViewById(R.id.btnUserHomepageProductBuyNow);

        btncancleproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(HomeFragmentproductdetails.this);
                progressDialog.setTitle("Deleting");
                progressDialog.setMessage("Please Wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                deleteuser();
            }
        });



        getproductdetails(strid);


    }
    private void deleteuser() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("id",strid);
        client.post(urls.deletproduct,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    boolean status = response.getBoolean("success");
                    if (status){
                        progressDialog.dismiss();
                        Intent i = new Intent(HomeFragmentproductdetails.this,adminhomeActivity.class);
                        startActivity(i);
                        Toast.makeText(HomeFragmentproductdetails.this, "User data Remove From Database", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(HomeFragmentproductdetails.this, "Unable to remove data", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(HomeFragmentproductdetails.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void getproductdetails(String strid) {

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();

            params.put("id", strid);

            client.post(urls.UserExplorProductdetails, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        JSONArray jsonArray = response.getJSONArray("getproductdetails");
                        for (int i = 0; i < jsonArray.length(); i++) {
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

                            Glide.with(HomeFragmentproductdetails.this)
                                    .load(urls.address + "images/" + strimage)
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
                    Toast.makeText(HomeFragmentproductdetails.this, "server Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
}