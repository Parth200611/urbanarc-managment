package com.example.urbanarc.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.Admin.ADAPTERCLASS.ADAPTERADminGETallDeliveryList;
import com.example.urbanarc.Admin.POJOCLASS.POJOADminGetALLDELIVERYLIst;
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

public class AdminDeliveryboydataActivity extends AppCompatActivity {
    ListView lvlist;
    SearchView svsearchdelivery;
    TextView tvnodelivery;
    List<POJOADminGetALLDELIVERYLIst> pojoaDminGetALLDELIVERYLIsts;
 ADAPTERADminGETallDeliveryList adapteraDminGETallDeliveryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_deliveryboydata);
        getWindow().setStatusBarColor(ContextCompat.getColor(AdminDeliveryboydataActivity.this,R.color.green));

        lvlist=findViewById(R.id.lvAdmingetalldeliveryDatalist);

        tvnodelivery=findViewById(R.id.tvAdmingetAlldeliverynouseravaiable);
        pojoaDminGetALLDELIVERYLIsts = new ArrayList<>();

        getAllDeliveryData();


    }

    private void getAllDeliveryData() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(urls.getAdminDeliveryboylist,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getalldeliveryboy");
                    if (jsonArray.isNull(0)){
                        tvnodelivery.setVisibility(View.VISIBLE);
                    }
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strid=jsonObject.getString("id");
                        String strname=jsonObject.getString("name");
                        String strusername=jsonObject.getString("username");
                        String strrating=jsonObject.getString("rating");
                        String stremialid=jsonObject.getString("emailid");
                        String strmobileno=jsonObject.getString("mobileno");
                        String strimage=jsonObject.getString("images");
                        String strpassword=jsonObject.getString("deliverypassword");
                        String strorder=jsonObject.getString("orders");
                        pojoaDminGetALLDELIVERYLIsts.add(new POJOADminGetALLDELIVERYLIst(strid,strname,strimage,strusername,strrating,stremialid,strmobileno,strorder,strpassword));
                    }
                    adapteraDminGETallDeliveryList = new ADAPTERADminGETallDeliveryList(pojoaDminGetALLDELIVERYLIsts,AdminDeliveryboydataActivity.this);
                    lvlist.setAdapter(adapteraDminGETallDeliveryList);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AdminDeliveryboydataActivity.this, "server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}