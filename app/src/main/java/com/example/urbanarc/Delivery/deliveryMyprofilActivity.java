package com.example.urbanarc.Delivery;

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
import com.example.urbanarc.Shopkeeper.ShopkeepermyprofilActivity;
import com.example.urbanarc.User.userMyprofilActivity;
import com.example.urbanarc.comman.VolleyMultipartRequest;
import com.example.urbanarc.comman.urls;
import com.example.urbanarc.signupActivity;
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

public class deliveryMyprofilActivity extends AppCompatActivity {
    CircleImageView cvdeliveryprofilimage;
    TextView tvname,tvemail,tvmobileno,tvusername,tvlogout;
    Button btneditimage;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String strusername;
    private  int pick_image_request=789;
    Bitmap bitmap;
    Uri filepath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_myprofil);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor=preferences.edit();
        strusername=preferences.getString("username","");
        getWindow().setStatusBarColor(ContextCompat.getColor(deliveryMyprofilActivity.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(deliveryMyprofilActivity.this,R.color.white));

        cvdeliveryprofilimage = findViewById(R.id.civDeliveryMyprofilprofilimage);
        btneditimage = findViewById(R.id.btnDeliveryMyprofileditimage);
        tvname = findViewById(R.id.tvDeliveryMyprofilName);
        tvemail = findViewById(R.id.tvDeliveryMyprofilEmail);
        tvmobileno = findViewById(R.id.tvDeliveryMyprofilMobileno);
        tvusername = findViewById(R.id.tvDeliveryMyprofilUsername);
        tvlogout = findViewById(R.id.tvDeliveryMyprofillogout);
        
        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logoutdelivery();
            }
        });
        btneditimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectUserProfileimage();

            }
        });


    }

    private void Logoutdelivery() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutdelivery(); // Call the logout function if the user confirms
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

    private void logoutdelivery() {
        editor.putBoolean("isLogindeliver", false);
        editor.apply();

        // Redirect to the LoginActivity
        Intent intent = new Intent(deliveryMyprofilActivity.this, signupActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(deliveryMyprofilActivity.this);
        progressDialog.setTitle("loading");
        progressDialog.setMessage("Please wait a while");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        getDeliverydetails();
    }

    private void getDeliverydetails() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strusername);

        client.post(urls.Deliverymyprofil,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getuserdetails");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strimage = jsonObject.getString("images");
                        String strname = jsonObject.getString("name");
                        String stremail = jsonObject.getString("emailid");
                        String strmobileno = jsonObject.getString("mobileno");
                        String strusername = jsonObject.getString("username");

                        tvname.setText(strname);
                        tvemail.setText(stremail);
                        tvmobileno.setText(strmobileno);
                        tvusername.setText(strusername);
                        Glide.with(deliveryMyprofilActivity.this)
                                .load(urls.address+"images/"+strimage)
                                .skipMemoryCache(true)
                                .error(R.drawable.noimage)
                                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                                .override(800, 800) // Resize the image to 800x800 pixels
                                .into(cvdeliveryprofilimage);
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
                Toast.makeText(deliveryMyprofilActivity.this, "sever error", Toast.LENGTH_SHORT).show();
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
                cvdeliveryprofilimage.setImageBitmap(bitmap);
                UserImageSaveTodatabase(bitmap,strusername);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void UserImageSaveTodatabase(Bitmap bitmap, String struername) {

        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.delveryimage, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(deliveryMyprofilActivity.this, "Image Save as Profil ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(deliveryMyprofilActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(deliveryMyprofilActivity.this, "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

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
        Volley.newRequestQueue(deliveryMyprofilActivity.this).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}