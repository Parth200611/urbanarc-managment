package com.example.urbanarc.User;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class loginforcustomerActivity extends AppCompatActivity {
    EditText etusername,etpassword;
    CheckBox chshowpassword;
    TextView tvforgotttenpassword,tvnewuser;
    Button btnlogin,btngoogle;

    ProgressDialog progressDialog;
GoogleSignInOptions googleSignInOptions;
GoogleSignInClient googleSignInClient;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginforcustomer);
        getWindow().setNavigationBarColor(ContextCompat.getColor(loginforcustomerActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(loginforcustomerActivity.this,R.color.logincolor));

        preferences = PreferenceManager.getDefaultSharedPreferences(loginforcustomerActivity.this);
        editor = preferences.edit();



        etusername = findViewById(R.id.etloginusername);
        etpassword = findViewById(R.id.etloginpassword);
        chshowpassword = findViewById(R.id.cbshowpassword);
        tvforgotttenpassword = findViewById(R.id.tvLoginForgottenpassword);
        tvnewuser = findViewById(R.id.tvloginneewuser);
        btnlogin = findViewById(R.id.btnloginlogin);
        btngoogle = findViewById(R.id.btnloginuserbygoogle);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(loginforcustomerActivity.this,googleSignInOptions);
        btngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            signIn();
            }
        });





        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etusername.getText().toString().isEmpty()){
                    etusername.setError("Please enter your username or mobileno or email address");
                }else if (etpassword.getText().toString().isEmpty()){
                    etpassword.setError("Please enter your password");
                }else {
                   progressDialog = new ProgressDialog(loginforcustomerActivity.this);
                   progressDialog.setTitle("Lgin In");
                   progressDialog.setMessage("Please wait");
                   progressDialog.setCanceledOnTouchOutside(false);
                   progressDialog.show();
                   userlogin();

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

        tvnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginforcustomerActivity.this, userregistrationActivity.class);
                startActivity(i);
            }
        });
        tvforgotttenpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginforcustomerActivity.this, usernumbercheckforforgottenpassworrd.class);
                startActivity(i);
            }
        });










    }

    private void signIn() {
        Intent i =googleSignInClient.getSignInIntent();
        startActivityForResult(i,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(loginforcustomerActivity.this, userhomeActivity.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                Toast.makeText(loginforcustomerActivity.this,"something went wroong",Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void userlogin() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",etusername.getText().toString());
        params.put("emailid",etusername.getText().toString());
        params.put("userpassword",etpassword.getText().toString());

        client.post(urls.Userlogin,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent i = new Intent(loginforcustomerActivity.this,userhomeActivity.class);
                        editor.putString("username",etusername.getText().toString()).commit();
                        editor.putBoolean("isLogin",true);
                        editor.apply();
                        startActivity(i);
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(loginforcustomerActivity.this, "Password or Username wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(loginforcustomerActivity.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}