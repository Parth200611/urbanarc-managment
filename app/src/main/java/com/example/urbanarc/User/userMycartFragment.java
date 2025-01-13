package com.example.urbanarc.User;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.urbanarc.R;
import com.example.urbanarc.User.ADAPTERCLASS.AdpterUserAddtocartdata;
import com.example.urbanarc.User.POJOCLASS.POJOUserGetAddtoCartdata;
import com.example.urbanarc.comman.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class userMycartFragment extends Fragment {
    SearchView svserachabr;
    RecyclerView rvlistofproduct;
    String strusername;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String strid,strusername1,strshopname,strcategory,strimage,strproductname,strprice,stroffer,strdiscription,strdelivery,strrating,strproductid;
    List<POJOUserGetAddtoCartdata> pojoUserGetAddtoCartdata;
    AdpterUserAddtocartdata adpterUserAddtocartdata;
    TextView tvnoproduct;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_mycart, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor=preferences.edit();
        strusername=preferences.getString("username",null);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        svserachabr=view.findViewById(R.id.svUseraddtoccartsearchbar);
        rvlistofproduct = view.findViewById(R.id.rvUserAddtocartList);
        tvnoproduct = view.findViewById(R.id.tvnoproduct);
        rvlistofproduct.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
        pojoUserGetAddtoCartdata=new ArrayList<>();
        getAllData(strusername);

        return   view;
    }

    private void getAllData(String strusername) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest= new StringRequest(Request.Method.POST, urls.Useraddtocart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("getaddtocartdata");
                    if (jsonArray.isNull(0)){
                        rvlistofproduct.setVisibility(View.GONE);
                        tvnoproduct.setVisibility(View.VISIBLE);
                    }
                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        strid=jsonObject1.getString("id");
                        strusername1=jsonObject1.getString("username");
                        strshopname=jsonObject1.getString("shopname");
                        strimage=jsonObject1.getString("image");
                        strcategory=jsonObject1.getString("category");
                        strproductname=jsonObject1.getString("productname");
                        strprice=jsonObject1.getString("price");
                        stroffer=jsonObject1.getString("offer");
                        strdiscription=jsonObject1.getString("discription");
                        strrating=jsonObject1.getString("rating");
                        strdelivery=jsonObject1.getString("deliveryday");
                        strproductid=jsonObject1.getString("productid");
                        pojoUserGetAddtoCartdata.add(new POJOUserGetAddtoCartdata(strid,strusername1,strshopname,strimage,strcategory,strproductname,strprice,stroffer,strdiscription,strrating,strdelivery,strproductid));

                    }
                    adpterUserAddtocartdata = new AdpterUserAddtocartdata(pojoUserGetAddtoCartdata,getActivity());
                    rvlistofproduct.setAdapter(adpterUserAddtocartdata);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parms = new HashMap<>();
                parms.put("username",strusername);
                return parms;
            }
        };
        requestQueue.add(stringRequest);
    }
}