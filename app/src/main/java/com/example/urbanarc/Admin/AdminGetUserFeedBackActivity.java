package com.example.urbanarc.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.urbanarc.Admin.ADAPTERCLASS.AdapterAdminGetAllFeedback;
import com.example.urbanarc.Admin.POJOCLASS.POJOAdmingetAllFeedback;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminGetUserFeedBackActivity extends AppCompatActivity {

    RecyclerView rvfeedbacklist;
    TextView tvnofeedback;
    List<POJOAdmingetAllFeedback> pojoAdmingetAllFeedbacks;
    AdapterAdminGetAllFeedback adapterAdminGetAllFeedback;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_get_user_feed_back);
        
        getWindow().setStatusBarColor(ContextCompat.getColor(AdminGetUserFeedBackActivity.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(AdminGetUserFeedBackActivity.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        

        rvfeedbacklist = findViewById(R.id.rvAdminGetFeedbackList);
        tvnofeedback = findViewById(R.id.tvAdminGetFeedbackNoFeedback);
        pojoAdmingetAllFeedbacks=new ArrayList<>();

        rvfeedbacklist.setLayoutManager(new GridLayoutManager(AdminGetUserFeedBackActivity.this,1
                ,GridLayoutManager.VERTICAL,false));
        adapterAdminGetAllFeedback = new AdapterAdminGetAllFeedback(pojoAdmingetAllFeedbacks,AdminGetUserFeedBackActivity.this);
        rvfeedbacklist.setAdapter(adapterAdminGetAllFeedback);

        
        GetAllFeedback();

    }

    private void GetAllFeedback() {
        RequestQueue requestQueue = Volley.newRequestQueue(AdminGetUserFeedBackActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls.AdminGetUserfeedback, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("getallfeedback");

                    if (jsonArray.isNull(0)){
                        rvfeedbacklist.setVisibility(View.GONE);
                        tvnofeedback.setVisibility(View.VISIBLE);
                    }

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strid=jsonObject1.getString("id");
                        String strusername=jsonObject1.getString("username");
                        String strimage=jsonObject1.getString("image");
                        String strmessage=jsonObject1.getString("message");
                        String strdate=jsonObject1.getString("date");
                        String strtime=jsonObject1.getString("time");
                        pojoAdmingetAllFeedbacks.add(new POJOAdmingetAllFeedback(strid,strusername,strimage,strmessage,strdate,strtime));
                    }
                    adapterAdminGetAllFeedback.notifyDataSetChanged();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminGetUserFeedBackActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }
}