package com.example.urbanarc.Admin;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

public class adminMyprofilActivity extends AppCompatActivity {

    CircleImageView civadminprofilimage;
    TextView tvname,tvemailid,tvmobileno,tvusername,tvlogout;

    ProgressDialog progressDialog;
    SharedPreferences preferences;
    String strusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_myprofil);

        preferences = PreferenceManager.getDefaultSharedPreferences(adminMyprofilActivity.this);
        strusername=preferences.getString("username","");

        getWindow().setStatusBarColor(ContextCompat.getColor(adminMyprofilActivity.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(adminMyprofilActivity.this,R.color.white));

        civadminprofilimage = findViewById(R.id.civAdminMyprofilprofilimage);
        tvname = findViewById(R.id.tvAdminMyprofilName);
        tvemailid = findViewById(R.id.tvAdminMyprofilEmail);
        tvmobileno = findViewById(R.id.tvAdminMyprofilMobileno);
        tvusername = findViewById(R.id.tvAdminMyprofilUsername);
        tvlogout = findViewById(R.id.tvAdminMyprofillogout);


    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(adminMyprofilActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait for while");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        getAdmindetails();
    }

    private void getAdmindetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strusername);

        client.post(urls.Adminmyprofil,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getuserdetails");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strname=jsonObject.getString("name");
                        String strimage=jsonObject.getString("images");
                        String stremail=jsonObject.getString("emailid");
                        String strmobileno=jsonObject.getString("mobileno");
                        String strusername=jsonObject.getString("username");

                        tvname.setText(strname);
                        tvemailid.setText(stremail);
                        tvmobileno.setText(strmobileno);
                        tvusername.setText(strusername);

                        Glide.with(adminMyprofilActivity.this)
                                .load(urls.address+"images/"+strimage)
                                .skipMemoryCache(true)
                                .error(R.drawable.noimage)
                                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                                .override(800, 800) // Resize the image to 800x800 pixels
                                .into(civadminprofilimage);

                        progressDialog.dismiss();

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(adminMyprofilActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}