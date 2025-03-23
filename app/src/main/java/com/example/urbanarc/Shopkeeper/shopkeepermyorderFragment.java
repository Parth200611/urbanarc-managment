package com.example.urbanarc.Shopkeeper;

import android.app.ProgressDialog;
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

import com.example.urbanarc.R;
import com.example.urbanarc.Shopkeeper.Adpter.AdpterOrders;
import com.example.urbanarc.Shopkeeper.POJO.POJOOrders;
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


public class shopkeepermyorderFragment extends Fragment {
    private RecyclerView rvProductList;
    private TextView tvNoProduct;
    SharedPreferences preferences;
    String strusername,strname;
    List<POJOOrders>pojoOrders;
    AdpterOrders adpterOrders;
    ProgressDialog progressDialog;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_shopkeepermyorder, container, false);
        preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        strusername = preferences.getString("username","");

        rvProductList = view.findViewById(R.id.rvProductList);
        tvNoProduct = view.findViewById(R.id.tvNoProduct);
        rvProductList.setLayoutManager(new LinearLayoutManager(getContext()));
        pojoOrders = new ArrayList<>();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.show();

        getShopdetails();
        getData(strname);

        return view;
    }

    private void getData(String strname) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("shopname",strname);
        client.post(urls.getOrders,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getallorders");
                    if (jsonArray.length()==0){
                        rvProductList.setVisibility(View.GONE);
                        tvNoProduct.setVisibility(View.VISIBLE);
                    }else{
                        rvProductList.setVisibility(View.VISIBLE);
                        tvNoProduct.setVisibility(View.GONE);
                    }
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String username = jsonObject.getString("username");
                        String name = jsonObject.getString("name");
                        String mobileno = jsonObject.getString("mobileno");
                        String lattitude = jsonObject.getString("lattitude");
                        String longitude = jsonObject.getString("longitude");
                        String address= jsonObject.getString("address");
                        String image = jsonObject.getString("image");
                        String productname = jsonObject.getString("productname");
                        String price = jsonObject.getString("price");
                        String offer = jsonObject.getString("offer");
                        String discription = jsonObject.getString("discription");
                        String delieryday = jsonObject.getString("delieryday");
                        String shopname = jsonObject.getString("shopname");
                        String category = jsonObject.getString("category");
                        String productid = jsonObject.getString("productid");
                        String payment = jsonObject.getString("payment");
                        pojoOrders.add(new POJOOrders(id,username,name,mobileno,lattitude,longitude,address,image,productname,price,offer,discription,delieryday,shopname,category,productid,payment));
                    }
                    adpterOrders= new AdpterOrders(pojoOrders,getActivity());
                    progressDialog.dismiss();
                    rvProductList.setAdapter(adpterOrders);
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