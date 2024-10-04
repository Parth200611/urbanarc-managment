package com.example.urbanarc.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class userMyprofilActivity extends AppCompatActivity {

    CircleImageView civprofilimage;
    Button btneditimage;
    TextView tvname,tvemailid,tvmobileno,tvusername,tveditdetails,tvlogout;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    String struername;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_myprofil);
        getWindow().setStatusBarColor(ContextCompat.getColor(userMyprofilActivity.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(userMyprofilActivity.this,R.color.white));
        preferences = PreferenceManager.getDefaultSharedPreferences(userMyprofilActivity.this);
        struername=preferences.getString("username","");

        civprofilimage = findViewById(R.id.civUserMyprofilprofilimage);
        btneditimage = findViewById(R.id.btnUserMyprofileditimage);
        tvname = findViewById(R.id.tvUserMyprofilName);
        tvemailid = findViewById(R.id.tvUserMyprofilEmail);
        tvmobileno = findViewById(R.id.tvUserMyprofilMobileno);
        tvusername = findViewById(R.id.tvUserMyprofilUsername);
        tveditdetails = findViewById(R.id.tvUserMyprofiledit);
        tvlogout = findViewById(R.id.tvUserMyprofillogout);

        googleSignInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(userMyprofilActivity.this,googleSignInOptions);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(userMyprofilActivity.this);
        if (googleSignInAccount != null){
            String name = googleSignInAccount.getDisplayName();
            String email = googleSignInAccount.getEmail();

            tvname.setText(name);
            tvemailid.setText(email);

            tvlogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent i = new Intent(userMyprofilActivity.this, loginforcustomerActivity.class);
                            startActivity(i);
                            finish();

                        }
                    });

                }
            });
        }
        tveditdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(userMyprofilActivity.this,userEditprofilActivity.class);
                startActivity(i);

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(userMyprofilActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait for while");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        getuserDetails();
    }

    private void getuserDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",struername);

        client.post(urls.Usermyprofil,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonArray = response.getJSONArray("getuserdetails");

                    for (int i = 0;i < jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strname = jsonObject.getString("name");
                        String strimage = jsonObject.getString("images");
                        String stremail = jsonObject.getString("emailid");
                        String strmobileno = jsonObject.getString("mobileno");
                        String strusername = jsonObject.getString("username");

                        progressDialog.dismiss();

                        tvname.setText(strname);
                        tvemailid.setText(stremail);
                        tvmobileno.setText(strmobileno);
                        tvusername.setText(strusername);

                        Glide.with(userMyprofilActivity.this)
                                .load(urls.address+"images/"+strimage)
                                .skipMemoryCache(true)
                                .error(R.drawable.noimage)
                                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                                .override(800, 800) // Resize the image to 800x800 pixels
                                .into(civprofilimage);

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(userMyprofilActivity.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}