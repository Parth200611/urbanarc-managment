package com.example.urbanarc.Admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.example.urbanarc.Admin.ADAPTERCLASS.AdpterAllProductorder;
import com.example.urbanarc.Admin.POJOCLASS.POJOALLOder;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class adminmanageordersFragment extends Fragment {
    RecyclerView rvlistoforedr;
    TextView tvnoorder;
    String strproductname,strdiscription,strprice,strrating,stroffer,strshopname,strdelivery,strid1,strcategory,strimage;
    List<POJOALLOder> pojoallOders;
    AdpterAllProductorder adpterAllProductorder;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_adminmanageorders, container, false);

        rvlistoforedr=view.findViewById(R.id.rvAllorders);
        tvnoorder=view.findViewById(R.id.tvnoorder);

        rvlistoforedr.setLayoutManager(new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false));
        pojoallOders=new ArrayList<>();
        getOrders();


        return view;

    }

    private void getOrders() {
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.getallOders, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Log.d("API Response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("getallorders");
                    if (jsonArray.length() == 0) {
                        // No orders found
                        rvlistoforedr.setVisibility(View.GONE);
                        tvnoorder.setVisibility(View.VISIBLE);
                    } else {
                        // Orders exist
                        rvlistoforedr.setVisibility(View.VISIBLE);
                        tvnoorder.setVisibility(View.GONE);
                    }
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String Username = jsonObject1.getString("username");
                        String name = jsonObject1.getString("name");
                        String mobile = jsonObject1.getString("mobileno");
                        String longitude = jsonObject1.getString("lattitude");
                        String lattitude = jsonObject1.getString("longitude");
                        String add = jsonObject1.getString("address");
                        strimage = jsonObject1.getString("image");
                        strproductname = jsonObject1.getString("productname");
                        strprice = jsonObject1.getString("price");
                        stroffer = jsonObject1.getString("offer");
                        strdiscription = jsonObject1.getString("discription");
                        strdelivery = jsonObject1.getString("delieryday");
                        strshopname = jsonObject1.getString("shopname");
                        strcategory = jsonObject1.getString("category");
                        String proid = jsonObject1.getString("productid");
                        String strpay = jsonObject1.getString("payment");
                        pojoallOders.add(new POJOALLOder(id,Username
                        ,name,mobile,add,strimage,strproductname,strprice,stroffer,strdiscription,strdelivery,strshopname,strcategory,proid,strpay));

                    }
                    adpterAllProductorder=new AdpterAllProductorder(pojoallOders,getActivity());
                    rvlistoforedr.setAdapter(adpterAllProductorder);
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