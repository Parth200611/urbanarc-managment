package com.example.urbanarc.User;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

public class UserHomepageProductdetailsSofa extends AppCompatActivity {
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
        setContentView(R.layout.activity_user_homepage_productdetails_sofa);
        getWindow().setStatusBarColor(ContextCompat.getColor(UserHomepageProductdetailsSofa.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(UserHomepageProductdetailsSofa.this,R.color.white));
        preferences = PreferenceManager.getDefaultSharedPreferences(UserHomepageProductdetailsSofa.this);
        editor = preferences.edit();
        strUsername=preferences.getString("username",null);
        shopname=preferences.getString("userhomesofashopname",null);
        image=preferences.getString("userhomesofaimage",null);
        category=preferences.getString("userhomesofacategory",null);
        productname=preferences.getString("userhomesofaproductname",null);
        price=preferences.getString("userhomesofaprice",null);
        offer=preferences.getString("userhomesofaoffer",null);
        discription=preferences.getString("userhomesofadiscription",null);
        rating=preferences.getString("userhomesofarating",null);
        deliverybay=preferences.getString("userhomesofadelivery",null);

        strid=getIntent().getStringExtra("id");

        tvproductname = findViewById(R.id.tvUserHomepageProductName);
        tvproductdiscription=findViewById(R.id.tvUserHomepageProductDiscription);
        tvproductprice=findViewById(R.id.tvUserHomepageProductPrice);
        tvproductrating=findViewById(R.id.tvUserHomepageProductRating);
        tvproductoffer=findViewById(R.id.tvUserHomepageProductoffer);
        ivproductimage=findViewById(R.id.ivUserHomepageProductDetailsSofaimage);
        tvshopname = findViewById(R.id.tvUserHomepageProductshopname);
        tvdeliveryday = findViewById(R.id.tvUserHomepageProductdelivery);
        ivAddtoFav = findViewById(R.id.heartIcon);
        
        ivAddtoFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHeart();
            }
        });

        getDetails(strid);

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
        params.put("radting",strrating);
        params.put("deliveryday",strdelivery);

        client.post(urls.Userhomepagefavlistproduct,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(UserHomepageProductdetailsSofa.this, "Added To Wishlist", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(UserHomepageProductdetailsSofa.this, "error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(UserHomepageProductdetailsSofa.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDetails(String strid) {
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
                        editor.putString("userhomesofaimage",strimage).commit();
                        editor.putString("userhomesofacategory",strcategory).commit();
                        editor.putString("userhomesofaproductname",strproductname).commit();
                        editor.putString("userhomesofaprice",strprice).commit();
                        editor.putString("userhomesofaoffer",stroffer).commit();
                        editor.putString("userhomesofadiscription",strdiscription).commit();
                        editor.putString("userhomesofarating",strrating).commit();
                        editor.putString("userhomesofashopname",strshopname).commit();
                        editor.putString("userhomesofadelivery",strdelivery).commit();
                        Glide.with(UserHomepageProductdetailsSofa.this)
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
                Toast.makeText(UserHomepageProductdetailsSofa.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}