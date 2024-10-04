package com.example.urbanarc.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

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

public class AdminShopkeeperlistActivity extends AppCompatActivity {
    SearchView svSearchShopkeeper;
    ListView lvShopkeeperList;
    TextView tvNoshopkeeper;
    List<POJOADMINgetallshopkeeperlist> pojoadmiNgetallshopkeeperlists;
    AdapterAdmingetallshopkeeper adapterAdmingetallshopkeeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_shopkeeperlist);
        getWindow().setStatusBarColor(ContextCompat.getColor(AdminShopkeeperlistActivity.this,R.color.green));

        svSearchShopkeeper=findViewById(R.id.svAdmingetShopkeeperlistSearchView);
        lvShopkeeperList = findViewById(R.id.lvAdminshopkeeperList);
        tvNoshopkeeper = findViewById(R.id.tvAdminShopkeeperlistNoshopkeeper);
        pojoadmiNgetallshopkeeperlists = new ArrayList<>();

        getListofShopkeeper();


    }

    private void getListofShopkeeper() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(urls.getAdminshopkeeperlist,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getshopkeeperdetails");
                    if (jsonArray.isNull(0)){
                        tvNoshopkeeper.setVisibility(View.VISIBLE);
                    }
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strid = jsonObject.getString("id");
                        String strimage = jsonObject.getString("images");
                        String strname = jsonObject.getString("name");
                        String stremailid = jsonObject.getString("emailid");
                        String strmobileno = jsonObject.getString("mobileno");
                        String straddress = jsonObject.getString("address");
                        String strusername = jsonObject.getString("username");
                        String strpassword = jsonObject.getString("shopkeeperpassword");

                        pojoadmiNgetallshopkeeperlists.add(new POJOADMINgetallshopkeeperlist(strid,strimage,strname,stremailid,strmobileno,straddress,strusername,strpassword));

                    }
                    adapterAdmingetallshopkeeper = new AdapterAdmingetallshopkeeper(pojoadmiNgetallshopkeeperlists,AdminShopkeeperlistActivity.this);
                    lvShopkeeperList.setAdapter(adapterAdmingetallshopkeeper);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AdminShopkeeperlistActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}