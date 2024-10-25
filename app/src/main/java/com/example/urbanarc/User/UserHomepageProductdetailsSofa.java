package com.example.urbanarc.User;

import android.app.ProgressDialog;
import android.os.Bundle;
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
    TextView tvproductname,tvproductdiscription,tvproductprice,tvproductoffer,tvproductrating;
    ImageView ivproductimage,ivAddtoFav;
    AppCompatButton btnBuynow,btnaddtocart;
    ProgressDialog progressDialog;
    String strid;
    private boolean isHeartFilled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage_productdetails_sofa);
        getWindow().setStatusBarColor(ContextCompat.getColor(UserHomepageProductdetailsSofa.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(UserHomepageProductdetailsSofa.this,R.color.white));
        strid=getIntent().getStringExtra("id");

        tvproductname = findViewById(R.id.tvUserHomepageProductName);
        tvproductdiscription=findViewById(R.id.tvUserHomepageProductDiscription);
        tvproductprice=findViewById(R.id.tvUserHomepageProductPrice);
        tvproductrating=findViewById(R.id.tvUserHomepageProductRating);
        tvproductoffer=findViewById(R.id.tvUserHomepageProductoffer);
        ivproductimage=findViewById(R.id.ivUserHomepageProductDetailsSofaimage);
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
                        String strid = jsonObject.getString("id");
                        String strimage = jsonObject.getString("image");
                        String strcategory = jsonObject.getString("category");
                        String strproductname = jsonObject.getString("productname");
                        String strprice = jsonObject.getString("price");
                        String stroffer = jsonObject.getString("offer");
                        String strdiscription = jsonObject.getString("discription");
                        String strrating = jsonObject.getString("rating");

                        tvproductname.setText(strproductname);
                        tvproductdiscription.setText(strdiscription);
                        tvproductprice.setText(strprice);
                        tvproductoffer.setText(stroffer);
                        tvproductrating.setText(strrating);
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