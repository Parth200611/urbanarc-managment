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

import com.example.urbanarc.Delivery.Adpter.AdpterHis;
import com.example.urbanarc.Delivery.POJO.POJODELI;
import com.example.urbanarc.Delivery.POJO.POJOHis;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class deliveryorderhistoryFragment extends Fragment {
    private RecyclerView rvProductList;
    private TextView tvNoProduct;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String strusername;
    ProgressDialog progressDialog;
    List<POJOHis>pojoHis;
    AdpterHis adpterHis;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_deliveryorderhistory, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor=preferences.edit();
        strusername=preferences.getString("username","");
        rvProductList = view.findViewById(R.id.rvProductList);
        tvNoProduct = view.findViewById(R.id.tvNoProduct);
        rvProductList.setLayoutManager(new LinearLayoutManager(getContext()));
        pojoHis=new ArrayList<>();
        progressDialog= new ProgressDialog(getContext());
        progressDialog.show();
        getData(strusername);
        return view;
    }

    private void getData(String strusername) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params =new RequestParams();
        params.put("username",strusername);
        client.post(urls.getHis,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getHis");
                    if (jsonArray.length() == 0) {
                        rvProductList.setVisibility(View.GONE);
                        tvNoProduct.setVisibility(View.VISIBLE);
                    } else {
                        rvProductList.setVisibility(View.VISIBLE);
                        tvNoProduct.setVisibility(View.GONE);
                    }

                    pojoHis.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String username = jsonObject1.getString("username");
                        String address = jsonObject1.getString("address");
                        String image = jsonObject1.getString("image");
                        String productname = jsonObject1.getString("productname");
                        String price = jsonObject1.getString("price");
                        String deluser = jsonObject1.getString("deluser");
                        String payment = jsonObject1.getString("payment");
                        pojoHis.add(new POJOHis(image,productname,price,username,address,payment,deluser));

                    }
                    adpterHis = new AdpterHis(pojoHis,getActivity());
                    rvProductList.setAdapter(adpterHis);
                    progressDialog.dismiss();
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