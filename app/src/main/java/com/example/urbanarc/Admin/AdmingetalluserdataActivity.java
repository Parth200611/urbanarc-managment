package com.example.urbanarc.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.Admin.ADAPTERCLASS.ADAPTERAdmingetalluser;
import com.example.urbanarc.Admin.POJOCLASS.POJOADMINGetallUserData;
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

public class AdmingetalluserdataActivity extends AppCompatActivity {


    ListView lvuserlist;
    TextView tvnouser,tvlocation;
    List<POJOADMINGetallUserData> pojoadminGetallUserData;
    ADAPTERAdmingetalluser adapterAdmingetalluser;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admingetalluserdata);
        getWindow().setStatusBarColor(ContextCompat.getColor(AdmingetalluserdataActivity.this,R.color.green));


        lvuserlist=findViewById(R.id.lvAdmingetalluserDatalist);
        tvnouser=findViewById(R.id.tvAdmingetAllusernouseravaiable);
        pojoadminGetallUserData=new ArrayList<>();
        searchView=findViewById(R.id.svAdminGetAllusersearchview);
        tvlocation=findViewById(R.id.tvAdminallUserdataLocation);

        tvlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdmingetalluserdataActivity.this, AdminAlluserLocationActivity.class);
                startActivity(i);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchuser(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchuser(query);
                return false;
            }
        });

        getAlluserdata();

    }

    private void searchuser(String query) {
        List<POJOADMINGetallUserData> tempsearch=new ArrayList<>();
        tempsearch.clear();

        for (POJOADMINGetallUserData obj:pojoadminGetallUserData) {
            if (obj.getName().toUpperCase().contains(query.toUpperCase()) ||
            obj.getMobileno().toUpperCase().contains(query.toUpperCase()) ||
            obj.getEmailid().toUpperCase().contains(query.toUpperCase()) ||
            obj.getUsername().toUpperCase().contains(query.toUpperCase()))

            {
                tempsearch.add(obj);
            }
            adapterAdmingetalluser = new ADAPTERAdmingetalluser(pojoadminGetallUserData,AdmingetalluserdataActivity.this);
            lvuserlist.setAdapter(adapterAdmingetalluser);

        }
    }

    private void getAlluserdata() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(urls.Getalluserdta,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getalluser");
                    if (jsonArray.isNull(0)){

                        tvnouser.setVisibility(View.VISIBLE);

                    }
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strid = jsonObject.getString("id");
                        String strimage = jsonObject.getString("images");
                        String strname = jsonObject.getString("name");
                        String stremailid = jsonObject.getString("emailid");
                        String strmobileno = jsonObject.getString("mobileno");
                        String strusername = jsonObject.getString("username");
                        String struserpassword = jsonObject.getString("userpassword");

                        pojoadminGetallUserData.add(new POJOADMINGetallUserData(strid,strimage,strname,stremailid,strmobileno,strusername,struserpassword));

                    }
                    adapterAdmingetalluser = new ADAPTERAdmingetalluser(pojoadminGetallUserData,AdmingetalluserdataActivity.this);
                    lvuserlist.setAdapter(adapterAdmingetalluser);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AdmingetalluserdataActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}