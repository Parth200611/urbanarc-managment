package com.example.urbanarc.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class UserFeedBackActivity extends AppCompatActivity {

    EditText etfeedback;
    Button btnsubmit;
    String strusername,strname,strdate,strtime,strimage;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_feed_back);
        getWindow().setNavigationBarColor(ContextCompat.getColor(UserFeedBackActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(UserFeedBackActivity.this,R.color.green));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        preferences= PreferenceManager.getDefaultSharedPreferences(UserFeedBackActivity.this);
        editor = preferences.edit();

        strname=preferences.getString("nameofuser","");
        strusername=preferences.getString("username","");
        strimage=preferences.getString("userimage",null);
        
        etfeedback=findViewById(R.id.etUserDeedbackFeedback);
        btnsubmit=findViewById(R.id.btnUserFeedbackSubmit);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        strdate = sdf.format(new Date());

        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        strtime= sd.format(new Date());
        
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etfeedback.getText().toString().isEmpty()) {
                    Toast.makeText(UserFeedBackActivity.this, "Enter Feedback", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog=new ProgressDialog(UserFeedBackActivity.this);
                    progressDialog.show();
                    SendFeedback();
                }
            }
        });
        

    }

    private void SendFeedback() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("username",strusername);
        params.put("image",strimage);
        params.put("message",etfeedback.getText().toString());
        params.put("date",strdate);
        params.put("time",strtime);

        client.post(urls.Userfeedback,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Toast.makeText(UserFeedBackActivity.this, "Thank Youn For Your FeedBack", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UserFeedBackActivity.this,userhomeActivity.class);
                        startActivity(i);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(UserFeedBackActivity.this, "Try After Some time", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(UserFeedBackActivity.this, "server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}