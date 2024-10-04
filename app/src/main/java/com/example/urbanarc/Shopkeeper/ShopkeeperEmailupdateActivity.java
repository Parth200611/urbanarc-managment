package com.example.urbanarc.Shopkeeper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ShopkeeperEmailupdateActivity extends AppCompatActivity {

    EditText etemailid;
    Button btnupdate;
    ProgressDialog progressDialog;
    String strusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_emailupdate);
        getWindow().setStatusBarColor(ContextCompat.getColor(ShopkeeperEmailupdateActivity.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(ShopkeeperEmailupdateActivity.this,R.color.white));

        etemailid = findViewById(R.id.etShopkeeperemailupdatenewemail);
        btnupdate = findViewById(R.id.btnShopkeeperemailupdateupdate);

        strusername=getIntent().getStringExtra("username");

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etemailid.getText().toString().contains("@") && !etemailid.getText().toString().contains(".com")) {
                    etemailid.setError("enter Proper Emaiil Id");
                } else if (etemailid.getText().toString().isEmpty()) {
                    etemailid.setError("enter email addreass");
                } else {
                    progressDialog = new ProgressDialog(ShopkeeperEmailupdateActivity.this);
                    progressDialog.setTitle("loading");
                    progressDialog.setMessage("Please wait for a while");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    ShopkeeperEmailupdate();
                }
            }
        });



    }

    private void ShopkeeperEmailupdate() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strusername);
        params.put("emailid",etemailid.getText().toString());

        client.post(urls.Shopkeeperemailupdate,params,new JsonHttpResponseHandler(){


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent i = new Intent(ShopkeeperEmailupdateActivity.this,ShopkeeperhomeActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(ShopkeeperEmailupdateActivity.this, "Updation Failed Try After Sometime", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(ShopkeeperEmailupdateActivity.this, "Server Errror", Toast.LENGTH_SHORT).show();
            }
        });
    }

}