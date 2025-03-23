package com.example.urbanarc.Shopkeeper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.example.urbanarc.R;
import com.example.urbanarc.User.userMyprofilActivity;
import com.example.urbanarc.comman.VolleyMultipartRequest;
import com.example.urbanarc.comman.urls;
import com.example.urbanarc.signupActivity;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShopkeepermyprofilActivity extends AppCompatActivity
{

    CircleImageView civShopkeeperprofilimage;
    TextView tvname,tvemailid,tvmoileno,tvusername,tvedit,tvloout,tvaddress;
    Button btneditimage;
    ProgressDialog progressDialog;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String strusername;
    private  int pick_image_request=789;
    Bitmap bitmap;
    Uri filepath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopkeepermyprofil);
        getWindow().setStatusBarColor(ContextCompat.getColor(ShopkeepermyprofilActivity.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(ShopkeepermyprofilActivity.this,R.color.white));

        preferences= PreferenceManager.getDefaultSharedPreferences(ShopkeepermyprofilActivity.this);
        editor=preferences.edit();
        strusername = preferences.getString("username","");

        civShopkeeperprofilimage = findViewById(R.id.civShopkeeperMyprofilprofilimage);
        btneditimage = findViewById(R.id.btnShopkeeperMyprofileditimage);
        tvname = findViewById(R.id.tvShopkeeperMyprofilName);
        tvemailid = findViewById(R.id.tvShopkeeperMyprofilEmail);
        tvmoileno = findViewById(R.id.tvShopkeeperMyprofilMobileno);
        tvusername = findViewById(R.id.tvShopkeeperMyprofilUsername);
        tvedit = findViewById(R.id.tvShopkeeperMyprofiledit);
        tvloout = findViewById(R.id.tvShopkeeperMyprofillogout);
        tvaddress = findViewById(R.id.tvShopkeeperMyprofilAddress);
        btneditimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectUserProfileimage();
            }
        });

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(ShopkeepermyprofilActivity.this,googleSignInOptions);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(ShopkeepermyprofilActivity.this);
        if (googleSignInAccount != null){
            String name = googleSignInAccount.getDisplayName();
            String email = googleSignInAccount.getEmail();

            tvname.setText(name);
            tvemailid.setText(email);

            tvloout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent i = new Intent(ShopkeepermyprofilActivity.this, loginforshopkeeperActivity.class);
                            startActivity(i);
                            finish();
                        }
                    });
                }
            });
        }

        tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShopkeepermyprofilActivity.this,ShopkeeperdetailsupdateActivity.class);
                startActivity(i);
            }
        });



        tvloout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopkeeperLogout();
            }
        });


    }

    private void ShopkeeperLogout() {

        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutshopkeeper(); // Call the logout function if the user confirms
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Close the dialog if the user cancels
                    }
                })
                .show();
    }

    private void logoutshopkeeper() {
        editor.putBoolean("isLoginshopkeeper", false);
        editor.apply();

        // Redirect to the LoginActivity
        Intent intent = new Intent(ShopkeepermyprofilActivity.this, signupActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(ShopkeepermyprofilActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait for while");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        getShopdetails();
    }

    private void getShopdetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Log.d("ShopkeeperProfile", "Fetching details for username: " + strusername); // Log username

        params.put("username",strusername);

        client.post(urls.Shopkeepermyprofil,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getuserdetails");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strname = jsonObject.getString("name");
                        String strimage = jsonObject.getString("images");
                        String stremailid = jsonObject.getString("emailid");
                        String strmobileno = jsonObject.getString("mobile");
                        String strusername = jsonObject.getString("username");
                        String straddress = jsonObject.getString("address");
                        progressDialog.dismiss();
                        tvname.setText(strname);
                        tvemailid.setText(stremailid);
                        tvmoileno.setText(strmobileno);
                        tvusername.setText(strusername);
                        tvaddress.setText(straddress);

                        Glide.with(ShopkeepermyprofilActivity.this)
                                .load(urls.address+"images/"+strimage)
                                .skipMemoryCache(true)
                                .error(R.drawable.noimage)
                                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                                .override(800, 800) // Resize the image to 800x800 pixels
                                .into(civShopkeeperprofilimage);

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(ShopkeepermyprofilActivity.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void SelectUserProfileimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image For Profil"),pick_image_request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==pick_image_request && resultCode==RESULT_OK && data!=null){
            filepath=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                civShopkeeperprofilimage.setImageBitmap(bitmap);
                UserImageSaveTodatabase(bitmap,strusername);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void UserImageSaveTodatabase(Bitmap bitmap, String struername) {

        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.shopkeperimage, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(ShopkeepermyprofilActivity.this, "Image Save as Profil ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShopkeepermyprofilActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(ShopkeepermyprofilActivity.this, "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("tags", struername); // Adjusted to match PHP parameter name
                return parms;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,VolleyMultipartRequest.DataPart> parms = new HashMap<>();
                long imagename = System.currentTimeMillis();
                parms.put("pic",new VolleyMultipartRequest.DataPart(imagename+".jpeg",getfiledatafromBitmap(bitmap)));

                return parms;

            }

        };
        Volley.newRequestQueue(ShopkeepermyprofilActivity.this).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}