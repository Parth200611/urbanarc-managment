package com.example.urbanarc.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class usernumbercheckforforgottenpassworrd extends AppCompatActivity {
    EditText etmobileno;
    Button btnverify;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usernumbercheckforforgottenpassworrd);
        getWindow().setNavigationBarColor(ContextCompat.getColor(usernumbercheckforforgottenpassworrd.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(usernumbercheckforforgottenpassworrd.this,R.color.green));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        etmobileno = findViewById(R.id.etUsernumbercheckmobileno);
        btnverify = findViewById(R.id.btnUsernumbercheckverify);

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etmobileno.getText().toString().isEmpty()){
                    etmobileno.setError("Enetr Mobile no");
                }else if (etmobileno.getText().toString().length()!=10){
                    etmobileno.setError("Emter proper mobile no");
                }else{
                    progressDialog = new ProgressDialog(usernumbercheckforforgottenpassworrd.this);
                    progressDialog.setTitle("verifying");
                    progressDialog.setMessage("Checking Your Number");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    checkmobileNo();
                }
            }
        });
    }

    private void checkmobileNo() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params =  new RequestParams();

        params.put("mobileno",etmobileno.getText().toString());
        client.post(urls.checkusermobilenumforforgottenpassword,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent i  = new Intent(usernumbercheckforforgottenpassworrd.this, userupdatepasswordActivity.class);
                        i.putExtra("mobileno",etmobileno.getText().toString());
                        startActivity(i);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(usernumbercheckforforgottenpassworrd.this, "Number Not Present", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(usernumbercheckforforgottenpassworrd.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}