package com.example.urbanarc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class loginforadminActivity extends AppCompatActivity {
    EditText etusername,etpassword;
    CheckBox chshowpassword;
    TextView tvdeliveryboy;
    Button btnlogin;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginforadmin);

        preferences = PreferenceManager.getDefaultSharedPreferences(loginforadminActivity.this);
        editor = preferences.edit();

        getWindow().setNavigationBarColor(ContextCompat.getColor(loginforadminActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(loginforadminActivity.this,R.color.green));


        etusername = findViewById(R.id.etloginotherusername);
        etpassword = findViewById(R.id.etloginotherassword);
        chshowpassword =  findViewById(R.id.cbLoginothershowpassword);
        tvdeliveryboy = findViewById(R.id.tvloginotherdeliveryboy);
        btnlogin = findViewById(R.id.btnloginotherlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etusername.getText().toString().isEmpty()){
                    etusername.setError("Please enter your username or mobileno or email address");
                }else if (etpassword.getText().toString().isEmpty()){
                    etpassword.setError("Please enter your password");
                }else {
                    progressDialog = new ProgressDialog(loginforadminActivity.this);
                    progressDialog.setTitle("Lgin In");
                    progressDialog.setMessage("Please wait");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    adminlogin();

                }
            }
        });

        chshowpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    etpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    etpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        tvdeliveryboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginforadminActivity.this,loginfordeliveryActivity.class);
                startActivity(i);
            }
        });






    }

    private void adminlogin() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",etusername.getText().toString());
        params.put("adminpassword",etpassword.getText().toString());

        client.post(urls.adminlogin,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent i = new Intent(loginforadminActivity.this,adminhomeActivity.class);
                        editor.putString("username",etusername.getText().toString()).commit();
                        startActivity(i);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(loginforadminActivity.this,"Data not Present",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(loginforadminActivity.this,"Data not Present",Toast.LENGTH_SHORT).show();
            }
        });

    }
}