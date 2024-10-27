package com.example.urbanarc.User;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.urbanarc.R;
import com.example.urbanarc.User.ADAPTERCLASS.AdpterHoomeGetCategoryWiseProduct;
import com.example.urbanarc.User.POJOCLASS.POJOgetCateggroryWisePoduct;
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

public class UserHomepagecategorywiseproduct extends AppCompatActivity {
    SearchView svsearchproduct;
    RecyclerView rvcategorywiseproduct;
    String category;
    String strid,strimage,strcategory,strproductname,strprice,stroffer,strdiscription,strrating,strdelivery;
    List<POJOgetCateggroryWisePoduct> pojOgetCateggroryWisePoducts;
    AdpterHoomeGetCategoryWiseProduct adpterHoomeGetCategoryWiseProduct;

    ListView lvlistofproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepagecategorywiseproduct);
        getWindow().setNavigationBarColor(ContextCompat.getColor(UserHomepagecategorywiseproduct.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(UserHomepagecategorywiseproduct.this,R.color.green));

        category=getIntent().getStringExtra("category");


        pojOgetCateggroryWisePoducts = new ArrayList<>();
        lvlistofproduct =  findViewById(R.id.lvCategorywiseproducthome);



        //getCategorywiseProduct(category);
        getcategoryproduct(category);

    }

    private void getcategoryproduct(String category) {
        AsyncHttpClient client =  new AsyncHttpClient();
        RequestParams params  = new RequestParams();

        params.put("category",category);
        client.post(urls.UsergetCategorywiseproduct,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray  = response.getJSONArray("getusercategoryhome");
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        strid=jsonObject1.getString("id");
                        strimage=jsonObject1.getString("image");
                        strcategory = jsonObject1.getString("category");
                        strproductname = jsonObject1.getString("productname");
                        strprice =jsonObject1.getString("price");
                        stroffer = jsonObject1.getString("offer");
                        strdiscription=jsonObject1.getString("discription");
                        strrating=jsonObject1.getString("rating");
                        strdelivery=jsonObject1.getString("delivery");
                        pojOgetCateggroryWisePoducts.add(new POJOgetCateggroryWisePoduct(strid,strimage,strcategory,strproductname,strprice,stroffer,strdiscription,strrating,strdelivery));

                    }
                    adpterHoomeGetCategoryWiseProduct = new AdpterHoomeGetCategoryWiseProduct(pojOgetCateggroryWisePoducts,UserHomepagecategorywiseproduct.this);
                    lvlistofproduct.setAdapter(adpterHoomeGetCategoryWiseProduct);

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

    private void getCategorywiseProduct(String category) {
        RequestQueue requestQueue = Volley.newRequestQueue(UserHomepagecategorywiseproduct.this);

        String urlWithParams = urls.UsergetCategorywiseproduct + "?category=" + Uri.encode(category);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlWithParams, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("getusercategoryhome");
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        strid=jsonObject1.getString("id");
                        strimage=jsonObject1.getString("image");
                        strcategory = jsonObject1.getString("category");
                        strproductname = jsonObject1.getString("productname");
                        strprice =jsonObject1.getString("price");
                        stroffer = jsonObject1.getString("offer");
                        strdiscription=jsonObject1.getString("discription");
                        strrating=jsonObject1.getString("rating");
                        strdelivery=jsonObject1.getString("delivery");
                       // pojOgetCateggroryWisePoducts.add(new POJOgetCateggroryWisePoduct(strid,strimage,strcategory,strproductname,strprice,stroffer,strdiscription,strrating,strdelivery));

                    }
                   // adpterHoomeGetCategoryWiseProduct= new AdpterHoomeGetCategoryWiseProduct(pojOgetCateggroryWisePoducts,UserHomepagecategorywiseproduct.this);
                   // rvcategorywiseproduct.setAdapter(adpterHoomeGetCategoryWiseProduct);
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