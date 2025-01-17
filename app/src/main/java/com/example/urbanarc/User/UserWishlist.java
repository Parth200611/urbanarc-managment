package com.example.urbanarc.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.urbanarc.R;
import com.example.urbanarc.User.ADAPTERCLASS.AdpterUserWishlist;
import com.example.urbanarc.User.POJOCLASS.POJOUserWishList;
import com.example.urbanarc.comman.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserWishlist extends AppCompatActivity {
    RecyclerView rvwishlist;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    String strusername;
    String strid,strusername1,strshopname,strimage,strcategory,strproductname,strprice,stroffer,strdiscription,strrating,strdelivery,strproductid;
    List<POJOUserWishList> pojoUserWishLists;
    AdpterUserWishlist adpterUserWishlist;
    TextView tvno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wishlist);
        getWindow().setStatusBarColor(ContextCompat.getColor(UserWishlist.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(UserWishlist.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        preferences = PreferenceManager.getDefaultSharedPreferences(UserWishlist.this);
        strusername = preferences.getString("username",null);
        rvwishlist = findViewById(R.id.rvUserwishlistrecyclerlist);
        pojoUserWishLists = new ArrayList<>();
        rvwishlist.setLayoutManager(new GridLayoutManager(UserWishlist.this,2,GridLayoutManager.VERTICAL,false));
        getwishistproduct(strusername);
        tvno=findViewById(R.id.tvnoorders);


    }

    private void getwishistproduct(String strusername) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.userWishlist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray  = jsonObject.getJSONArray("getuserwishlist");
                    if (jsonArray.length() == 0) {
                        // No orders found
                        rvwishlist.setVisibility(View.GONE);
                        tvno.setVisibility(View.VISIBLE);
                    } else {
                        // Orders exist
                        rvwishlist.setVisibility(View.VISIBLE);
                        tvno.setVisibility(View.GONE);
                    }
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        strid = jsonObject1.getString("id");
                        strusername1 = jsonObject1.getString("username");
                        strshopname = jsonObject1.getString("shopname");
                        strimage = jsonObject1.getString("image");
                       strcategory = jsonObject1.getString("category");
                       strproductname = jsonObject1.getString("productname");
                       strprice = jsonObject1.getString("price");
                       stroffer = jsonObject1.getString("offer");
                       strdiscription = jsonObject1.getString("discription");
                       strrating = jsonObject1.getString("rating");
                       strdelivery = jsonObject1.getString("delivery");
                       strproductid = jsonObject1.getString("productid");
                       pojoUserWishLists.add(new POJOUserWishList(strid,strusername1,strshopname,strimage,strcategory,strproductname,strprice,stroffer,strdiscription,strdelivery,strproductid,strrating));

                    }
                    adpterUserWishlist = new AdpterUserWishlist(pojoUserWishLists,UserWishlist.this);
                    rvwishlist.setAdapter(adpterUserWishlist);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parmms = new HashMap<>();
                parmms.put("username",strusername);
                return parmms;
            }
        };

        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UserWishlist.this, userhomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish(); // Optional: Close the current activity
    }

}