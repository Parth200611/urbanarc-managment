package com.example.urbanarc.Delivery;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
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
import com.example.urbanarc.Delivery.Adpter.AdpterDEli;
import com.example.urbanarc.Delivery.POJO.POJODELI;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class deliveryordersFragment extends Fragment {
    private RecyclerView rvProductList;
    private TextView tvNoProduct;
    List<POJODELI>pojodeliList;
    AdpterDEli adpterDEli;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String strusername;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_deliveryorders, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor=preferences.edit();
        strusername=preferences.getString("username","");
        rvProductList = view.findViewById(R.id.rvProductList);
        tvNoProduct = view.findViewById(R.id.tvNoProduct);
        rvProductList.setLayoutManager(new LinearLayoutManager(getContext()));
        pojodeliList=new ArrayList<>();
        progressDialog= new ProgressDialog(getContext());
        progressDialog.show();
        getData();
        return view;
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.getOrderdelivery, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("getOrder");
                    if (jsonArray.length() == 0) {
                        rvProductList.setVisibility(View.GONE);
                        tvNoProduct.setVisibility(View.VISIBLE);
                    } else {
                        rvProductList.setVisibility(View.VISIBLE);
                        tvNoProduct.setVisibility(View.GONE);
                    }

                    pojodeliList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String username = jsonObject1.getString("username");
                        String name = jsonObject1.getString("name");
                        String mobile = jsonObject1.getString("mobile");
                        String lat = jsonObject1.getString("lattitude");
                        String lon = jsonObject1.getString("lon");
                        String address = jsonObject1.getString("address");
                        String image = jsonObject1.getString("image");
                        String productname = jsonObject1.getString("productname");
                        String price = jsonObject1.getString("price");
                        String offer = jsonObject1.getString("offer");
                        String dis = jsonObject1.getString("dis");
                        String deliveryday = jsonObject1.getString("delivery");
                        String shopname = jsonObject1.getString("shopname");
                        String category = jsonObject1.getString("category");
                        String proid = jsonObject1.getString("proid");
                        String payment = jsonObject1.getString("payment");
                        pojodeliList.add(new POJODELI(id,username,name,mobile,strusername,image,address,image,productname,price,offer,dis,deliveryday,shopname,category,proid,payment));
                    }
                    adpterDEli = new AdpterDEli(pojodeliList,getActivity());
                    rvProductList.setAdapter(adpterDEli);
                    progressDialog.dismiss();

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