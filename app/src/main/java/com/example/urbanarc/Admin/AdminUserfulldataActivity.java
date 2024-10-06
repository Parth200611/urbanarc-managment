package com.example.urbanarc.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdminUserfulldataActivity extends AppCompatActivity {

    CircleImageView civuserimage;
    TextView tvname,tvmobileno,tvemailid,tvusername,tvpassword;
    AppCompatButton btnremoveuser;
    String strusername;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_userfulldata);
        getWindow().setStatusBarColor(ContextCompat.getColor(AdminUserfulldataActivity.this,R.color.green));

        civuserimage=findViewById(R.id.civAdminuserfulldatauserimage);
        tvname=findViewById(R.id.tvAdminUserfulldataofuserName);
        tvmobileno = findViewById(R.id.tvAdminfulldataofuserMobileno);
        tvemailid=findViewById(R.id.tvAdminUserfulldatauserEmailid);
        tvusername=findViewById(R.id.tvAdminUserfullDataUsername);
        tvpassword=findViewById(R.id.tvAdminUserfullDataPassword);
        btnremoveuser=findViewById(R.id.btnAdminUserfulldataRemoveuser);

        strusername=getIntent().getStringExtra("username");

        btnremoveuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(AdminUserfulldataActivity.this);
                progressDialog.setTitle("Deleting");
                progressDialog.setMessage("Please Wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                deleteuser();
            }
        });


        getFullInfoofUser();


    }



    private void getFullInfoofUser() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strusername);
        client.post(urls.GetalluserdatafullAdmin,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getuserdata");
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String strid = jsonObject.getString("id");
                        String strimage = jsonObject.getString("images");
                        String strname = jsonObject.getString("name");
                        String stremailid = jsonObject.getString("emailid");
                        String strmobileno = jsonObject.getString("mobileno");
                        String strusername = jsonObject.getString("username");
                        String struserpassword = jsonObject.getString("userpassword");

                        tvname.setText(strname);
                        tvemailid.setText(stremailid);
                        tvmobileno.setText(strmobileno);
                        tvusername.setText(strusername);
                        tvpassword.setText(struserpassword);
                        Glide.with(AdminUserfulldataActivity.this)
                                .load(urls.address+"images/"+strimage)
                                .skipMemoryCache(true)
                                .error(R.drawable.noimage)
                                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                                .override(800, 800) // Resize the image to 800x800 pixels
                                .into(civuserimage);

                    }









                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AdminUserfulldataActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void deleteuser() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strusername);
        client.post(urls.DeleteUser,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    boolean status = response.getBoolean("success");
                    if (status){
                        progressDialog.dismiss();
                        Intent i = new Intent(AdminUserfulldataActivity.this,AdmingetalluserdataActivity.class);
                        startActivity(i);
                        Toast.makeText(AdminUserfulldataActivity.this, "User data Remove From Database", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(AdminUserfulldataActivity.this, "Unable to remove data", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AdminUserfulldataActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}