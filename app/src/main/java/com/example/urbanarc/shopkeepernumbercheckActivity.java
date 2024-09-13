package com.example.urbanarc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class shopkeepernumbercheckActivity extends AppCompatActivity {
    EditText etmobileno;
    Button btnverify;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeepernumbercheck);
        getWindow().setNavigationBarColor(ContextCompat.getColor(shopkeepernumbercheckActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(shopkeepernumbercheckActivity.this,R.color.green));
        etmobileno = findViewById(R.id.etShopkeepernumbercheckmobileno);
        btnverify = findViewById(R.id.btnShopkeepernumbercheckverify);

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etmobileno.getText().toString().isEmpty()){
                    etmobileno.setError("Please enter mobileno");
                }else if(etmobileno.getText().toString().length()!=10){
                    etmobileno.setError("Enetr proper mobileno");
                }else{
                    progressDialog = new ProgressDialog(shopkeepernumbercheckActivity.this);
                    progressDialog.setTitle("verifying");
                    progressDialog.setMessage("please wait");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    Checknumber();
                }
            }
        });

    }

    private void Checknumber() {
        AsyncHttpClient client = new AsyncHttpClient();//client server commnunicationn
        RequestParams params = new RequestParams();//data put in srver

        params.put("mobileno",etmobileno.getText().toString());//key,data

        client.post(urls.Shopkeepernumbercheck,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent i = new Intent(shopkeepernumbercheckActivity.this, shopkeeperpasswordupdateActivity.class);
                        i.putExtra("mobileno",etmobileno.getText().toString());
                        startActivity(i);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(shopkeepernumbercheckActivity.this, "Number Not Present", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(shopkeepernumbercheckActivity.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}