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

public class UsercheckusernamefoeemailupdateActivity extends AppCompatActivity {

    EditText etusername;
    Button btnverify;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usercheckusernamefoeemailupdate);
        getWindow().setNavigationBarColor(ContextCompat.getColor(UsercheckusernamefoeemailupdateActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(UsercheckusernamefoeemailupdateActivity.this,R.color.green));

        etusername = findViewById(R.id.etUserCheckusernameforemailUsername);
        btnverify = findViewById(R.id.btnUsercheckusernameforemailVerify);

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etusername.getText().toString().isEmpty()) {
                    Toast.makeText(UsercheckusernamefoeemailupdateActivity.this, "Eneter username", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog = new ProgressDialog(UsercheckusernamefoeemailupdateActivity.this);
                    progressDialog.setTitle("Loading");
                    progressDialog.setMessage("Please wait a while");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    Checkusername();
                }
            }



        });

    }

    private void Checkusername() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",etusername.getText().toString());

        client.post(urls.Userusernamecheck,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent i = new Intent(UsercheckusernamefoeemailupdateActivity.this,UseremailupdateActivity.class);
                        i.putExtra("username",etusername.getText().toString());
                        startActivity(i);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(UsercheckusernamefoeemailupdateActivity.this, "Username not present", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(UsercheckusernamefoeemailupdateActivity.this, "server error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}