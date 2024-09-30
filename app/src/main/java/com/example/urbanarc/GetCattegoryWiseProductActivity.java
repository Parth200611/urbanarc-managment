package com.example.urbanarc;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.contextaware.ContextAware;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

public class GetCattegoryWiseProductActivity extends AppCompatActivity {

    SearchView svserchproduct;
    ListView lvlistofproduct;
    TextView tvnoproduct;
    String strcategoryname;
    List<POJOgetcategorywiseproduct> pojOgetcategorywiseproducts;
    Adapterclassforcategorywiseproduct adapterclassforcategorywiseproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_cattegory_wise_product);
        getWindow().setStatusBarColor(ContextCompat.getColor(GetCattegoryWiseProductActivity.this,R.color.green));

        svserchproduct = findViewById(R.id.svgetCategorywiseproductsearchproduct);
        lvlistofproduct = findViewById(R.id.lvgetCategorywiseproductlistofproduct);
        tvnoproduct = findViewById(R.id.tvgetCategorywiseproductnoproductavaiable);
        pojOgetcategorywiseproducts = new ArrayList<>();

        strcategoryname=getIntent().getStringExtra("categoryname");

        svserchproduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchproduct(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchproduct(query);
                return false;
            }
        });

        getCategorywiseproduct();
    }

    private void searchproduct(String query) {
        List<POJOgetcategorywiseproduct> templist=new ArrayList<>();
        templist.clear();
        for(POJOgetcategorywiseproduct obj:pojOgetcategorywiseproducts){
            if (obj.getCategoryname().toUpperCase().contains(query.toUpperCase())
            || obj.getCategoryShopname().toUpperCase().contains(query.toUpperCase())
            || obj.getCategoryproductname().toUpperCase().contains(query.toUpperCase())
            || obj.getCategoryproductprice().toUpperCase().contains(query.toUpperCase())){

                templist.add(obj);
            }
            adapterclassforcategorywiseproduct = new Adapterclassforcategorywiseproduct(templist,this);
            lvlistofproduct.setAdapter(adapterclassforcategorywiseproduct);

        }
    }

    private void getCategorywiseproduct() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("categoryname",strcategoryname);
        client.post(urls.Getallcategoryproduct,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonArray = response.getJSONArray("getcategoraywiseproduct");
                    if (jsonArray.isNull(0)){
                        tvnoproduct.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strid = jsonObject.getString("id");
                        String strcategoryname = jsonObject.getString("categoryname");
                        String strproductimage = jsonObject.getString("productimage");
                        String strcategoryproductname = jsonObject.getString("categoryproductname");
                        String strcategoryShopname = jsonObject.getString("categoryShopname");
                        String strcategoryproductprice = jsonObject.getString("categoryproductprice");
                        String strproductdiscription = jsonObject.getString("productdiscription");
                        String strproductoffer = jsonObject.getString("productoffer");
                        String strproductrating = jsonObject.getString("productrating");

                        pojOgetcategorywiseproducts.add(new POJOgetcategorywiseproduct(strid,strcategoryname,strproductimage,strcategoryproductname,strcategoryShopname,strcategoryproductprice
                        ,strproductdiscription,strproductoffer,strproductrating));

                    }
                    adapterclassforcategorywiseproduct = new Adapterclassforcategorywiseproduct(pojOgetcategorywiseproducts,GetCattegoryWiseProductActivity.this);
                    lvlistofproduct.setAdapter(adapterclassforcategorywiseproduct);





                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(GetCattegoryWiseProductActivity.this, "server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}