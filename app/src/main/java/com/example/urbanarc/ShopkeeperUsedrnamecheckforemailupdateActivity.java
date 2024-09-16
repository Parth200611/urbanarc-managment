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

public class ShopkeeperUsedrnamecheckforemailupdateActivity extends AppCompatActivity {
    EditText etUsername;
    Button btnverify;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopkeeper_usedrnamecheckforemailupdate);
        getWindow().setNavigationBarColor(ContextCompat.getColor(ShopkeeperUsedrnamecheckforemailupdateActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(ShopkeeperUsedrnamecheckforemailupdateActivity.this,R.color.green));

        etUsername = findViewById(R.id.etShopkeeperCheckusernameforemailUsername);
        btnverify = findViewById(R.id.btnShopkeepercheckusernameforemailVerify);

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().isEmpty()){
                    etUsername.setError("Enter the username");
                }else{
                    progressDialog = new ProgressDialog(ShopkeeperUsedrnamecheckforemailupdateActivity.this);
                    progressDialog.setTitle("Verifying");
                    progressDialog.setMessage("Please Wait a While");
                    progressDialog.show();

                    Checkusername();

                }
            }
        });
    }

    private void Checkusername() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",etUsername.getText().toString());

        client.post(urls.Shopkeeperusernamecheckemailupdate,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent i = new Intent(ShopkeeperUsedrnamecheckforemailupdateActivity.this, ShopkeeperEmailupdateActivity.class);
                        i.putExtra("username",etUsername.getText().toString());
                        startActivity(i);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(ShopkeeperUsedrnamecheckforemailupdateActivity.this, "Username is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(ShopkeeperUsedrnamecheckforemailupdateActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}