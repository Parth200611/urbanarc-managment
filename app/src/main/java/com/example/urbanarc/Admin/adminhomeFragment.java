package com.example.urbanarc.Admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.urbanarc.Admin.ADAPTERCLASS.ADpterAllProduct;
import com.example.urbanarc.Admin.POJOCLASS.POJOAllProduct;
import com.example.urbanarc.R;
import com.example.urbanarc.User.ADAPTERCLASS.AdpterExplordataget;
import com.example.urbanarc.User.POJOCLASS.POJOExplordataget;
import com.example.urbanarc.comman.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class adminhomeFragment extends Fragment {
    RecyclerView rvexplorlist;
    TextView tvnoproductavaiable;

    List<POJOAllProduct> pojoAllProducts;
    ADpterAllProduct adepterallproduct;
    String id,image,category,productname,price,offer,discription,rating;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        View view= inflater.inflate(R.layout.fragment_adminhome, container, false);
        rvexplorlist=view.findViewById(R.id.rvUserExplorList);
        tvnoproductavaiable=view.findViewById(R.id.tvnoproduct);
        rvexplorlist.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
        pojoAllProducts = new ArrayList<>();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        getAllProduct();







        return view;
    }
    private void getAllProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.UserExplorProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("getuserexplorsection");
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        id = jsonObject1.getString("id");
                        image = jsonObject1.getString("image");
                        category = jsonObject1.getString("category");
                        productname = jsonObject1.getString("productname");
                        price = jsonObject1.getString("price");
                        offer = jsonObject1.getString("offer");
                        discription = jsonObject1.getString("discription");
                        rating = jsonObject1.getString("rating");
                        pojoAllProducts.add(new POJOAllProduct(id,image,category,productname,price,offer,discription,rating));

                    }
                    adepterallproduct = new ADpterAllProduct(pojoAllProducts,getActivity());
                    rvexplorlist.setAdapter(adepterallproduct);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

}