package com.example.urbanarc.Shopkeeper;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.example.urbanarc.R;
import com.example.urbanarc.Shopkeeper.Adpter.AdpterAllProduct;
import com.example.urbanarc.Shopkeeper.POJO.POJOAllProduct;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class shopkeeperhomeFragment extends Fragment {
    private RecyclerView rvProductList;
    private TextView tvNoProduct;
    List<POJOAllProduct>pojoAllProducts;
    AdpterAllProduct adpterAllProduct;
    SharedPreferences preferences;
    String strusername,strname;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_shopkeeperhome, container, false);
        preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        strusername = preferences.getString("username","");

        rvProductList = view.findViewById(R.id.rvProductList);
        tvNoProduct = view.findViewById(R.id.tvNoProduct);
        rvProductList.setLayoutManager(new LinearLayoutManager(getContext()));
        pojoAllProducts=new ArrayList<>();
getShopdetails();
getData(strname);
        return view;
    }

    private void getData(String strname) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.getShopProduct,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("getShopProduct");

                            if (jsonArray.length() == 0) {
                                rvProductList.setVisibility(View.GONE);
                                tvNoProduct.setVisibility(View.VISIBLE);
                            } else {
                                rvProductList.setVisibility(View.VISIBLE);
                                tvNoProduct.setVisibility(View.GONE);
                            }

                            pojoAllProducts.clear();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String id = jsonObject1.getString("id");
                                String image = jsonObject1.getString("image");
                                String category = jsonObject1.getString("category");
                                String productname = jsonObject1.getString("productname");
                                String price = jsonObject1.getString("price");
                                String offer = jsonObject1.getString("offer");
                                String description = jsonObject1.getString("discription");
                                String rating = jsonObject1.getString("rating");
                                String deliveryday = jsonObject1.getString("deliveryday");

                                pojoAllProducts.add(new POJOAllProduct(id, image, category, productname, price, offer, description, rating, deliveryday));
                            }

                            adpterAllProduct = new AdpterAllProduct(pojoAllProducts, getActivity());
                            rvProductList.setAdapter(adpterAllProduct);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSON_ERROR", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY_ERROR", "Error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("shopname", strname);  // Sending the shop name as a parameter
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void getShopdetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Log.d("ShopkeeperProfile", "Fetching details for username: " + strusername); // Log username

        params.put("username",strusername);

        client.post(urls.Shopkeepermyprofil,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getuserdetails");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                         strname = jsonObject.getString("name");




                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}