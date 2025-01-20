package com.example.urbanarc.User;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.urbanarc.R;
import com.example.urbanarc.User.ADAPTERCLASS.AdpterMyOrders;
import com.example.urbanarc.User.ADAPTERCLASS.AdpterUserWishlist;
import com.example.urbanarc.User.POJOCLASS.POJOMYORDERS;
import com.example.urbanarc.User.POJOCLASS.POJOUserWishList;
import com.example.urbanarc.comman.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserOrders extends AppCompatActivity {
    RecyclerView rvlistofproduct;
    String username;
    TextView tvnoorders;
    SharedPreferences preferences;
    String image,name;
   List<POJOMYORDERS> pojomyorders;
   AdpterMyOrders adpterMyOrders;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_orders);
        getWindow().setStatusBarColor(ContextCompat.getColor(UserOrders.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(UserOrders.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        preferences = PreferenceManager.getDefaultSharedPreferences(UserOrders.this);
        username=preferences.getString("username",null);
        rvlistofproduct=findViewById(R.id.rvUserOrders);
        tvnoorders=findViewById(R.id.tvnoorders);
        rvlistofproduct.setLayoutManager(new GridLayoutManager(UserOrders.this,1,GridLayoutManager.VERTICAL,false));
        pojomyorders = new ArrayList<>();
        
        getOrders();
        
        
        
    }

    private void getOrders() {
        RequestQueue requestQueue = Volley.newRequestQueue(UserOrders.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.UserMyOrders, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray  = jsonObject.getJSONArray("getallorders");
                    if (jsonArray.length() == 0) {
                        // No orders found
                        rvlistofproduct.setVisibility(View.GONE);
                        tvnoorders.setVisibility(View.VISIBLE);
                    } else {
                        // Orders exist
                        rvlistofproduct.setVisibility(View.VISIBLE);
                        tvnoorders.setVisibility(View.GONE);
                    }

                        for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        image=jsonObject1.getString("image");
                        name=jsonObject1.getString("productname");
                        String productid=jsonObject1.getString("productid");
                        Log.d("ProductIDCheck", "Product ID: " + productid); // Log the product ID
                        pojomyorders.add(new POJOMYORDERS(image,name,productid));

                    }
                    adpterMyOrders = new AdpterMyOrders(pojomyorders,UserOrders.this);
                    rvlistofproduct.setAdapter(adpterMyOrders);

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
                parmms.put("username",username);
                return parmms;
            }
        };

        requestQueue.add(stringRequest);
    }
}