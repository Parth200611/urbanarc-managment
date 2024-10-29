package com.example.urbanarc.User;

import android.content.Intent;
import android.os.Bundle;
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

public class userwishlistproductdetails extends AppCompatActivity {

    TextView tvproductname,tvdiscription,tvprice,tvrating,tvoffer,tvdelivery,tvshopname;
    ImageView ivproductimage,ivAddtoFav;
    AppCompatButton btnaddtocart,btnbuynow;
    String strid;
    String strproductname,strdiscription,strprice,strrating,stroffer,strshopnem,strdelivery,strid1,strcategory,strimage;
    boolean isRed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userwishlistproductdetails);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.green));

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
}