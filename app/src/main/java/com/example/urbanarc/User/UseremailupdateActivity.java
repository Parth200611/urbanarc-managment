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
import androidx.core.content.ContextCompat;

import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UseremailupdateActivity extends AppCompatActivity {
    EditText etemailid;
    Button btnupdate;
    String strusername;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_useremailupdate);
        getWindow().setStatusBarColor(ContextCompat.getColor(UseremailupdateActivity.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(UseremailupdateActivity.this,R.color.white));
        etemailid = findViewById(R.id.etUseremailupdatenewemail);
        btnupdate = findViewById(R.id.btnUseremailupdateVerify);

        strusername=getIntent().getStringExtra("username");

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etemailid.getText().toString().contains("@") && !etemailid.getText().toString().contains(".com")) {
                    etemailid.setError("enter Proper Emaiil Id");
                }else if (etemailid.getText().toString().isEmpty()){
                    etemailid.setError("enter email addreass");
                }else {
                    progressDialog = new ProgressDialog(UseremailupdateActivity.this);
                    progressDialog.setTitle("loading");
                    progressDialog.setMessage("Please wait for a while");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    UpdateuserEmailid();
                }
            }
        });

    }

    private void UpdateuserEmailid() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strusername);
        params.put("emailid",etemailid.getText().toString());

        client.post(urls.Useremailupdate,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent i = new Intent(UseremailupdateActivity.this,userhomeActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(UseremailupdateActivity.this, "Updation Fail", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(UseremailupdateActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}