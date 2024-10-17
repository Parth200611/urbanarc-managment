package com.example.urbanarc.User;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class userregisterverificationActivity extends AppCompatActivity {
    TextView tvmobileno, tvresendotp;
    EditText etinput1, etinput2, etinput3, etinput4, etinput5, etinput6;
    Button btnverify;

    String strotp, strname, stremailid, strmobileno, strusername, strpassword;

    ProgressDialog progressDialog;

    double lattitude, longitude;
    String address;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregisterverification);
        getWindow().setStatusBarColor(ContextCompat.getColor(userregisterverificationActivity.this, R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(userregisterverificationActivity.this, R.color.white));

        tvmobileno = findViewById(R.id.tvUserregistrationverificationmobileno);
        tvresendotp = findViewById(R.id.tvUserregistrationresendotp);
        etinput1 = findViewById(R.id.etUserregistrationotpinput1);
        etinput2 = findViewById(R.id.etUserregistrationotpinput2);
        etinput3 = findViewById(R.id.etUserregistrationotpinput3);
        etinput4 = findViewById(R.id.etUserregistrationotpinput4);
        etinput5 = findViewById(R.id.etUserregistrationotpinput5);
        etinput6 = findViewById(R.id.etUserregistrationotpinput6);
        btnverify = findViewById(R.id.btUserregiistrationverify);


        if (ActivityCompat.checkSelfPermission(userregisterverificationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(userregisterverificationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(userregisterverificationActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 199);
        } else {

        }


        strotp = getIntent().getStringExtra("otp");
        strname = getIntent().getStringExtra("name");
        stremailid = getIntent().getStringExtra("emailid");
        strmobileno = getIntent().getStringExtra("mobileno");
        strusername = getIntent().getStringExtra("username");
        strpassword = getIntent().getStringExtra("password");
        tvmobileno.setText(strmobileno);
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etinput1.getText().toString().trim().isEmpty() || etinput2.getText().toString().trim().isEmpty() ||
                        etinput3.getText().toString().trim().isEmpty() ||
                        etinput4.getText().toString().trim().isEmpty() ||
                        etinput5.getText().toString().trim().isEmpty() ||
                        etinput6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(userregisterverificationActivity.this, "Enter Proper Otp", Toast.LENGTH_SHORT).show();
                }
                String otpcode = etinput1.getText().toString() + etinput2.getText().toString() + etinput3.getText().toString() + etinput4.getText().toString() + etinput5.getText().toString() + etinput6.getText().toString();

                if (strotp != null) {
                    progressDialog = new ProgressDialog(userregisterverificationActivity.this);
                    progressDialog.setTitle("Verifying");
                    progressDialog.setMessage("Please Wait for moment");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(strotp, otpcode);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                UserCurrentLocation();
                            } else {
                                Toast.makeText(userregisterverificationActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }


            }
        });

        tvresendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + strmobileno, 60, TimeUnit.SECONDS, userregisterverificationActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String newotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(newotp, forceResendingToken);
                        strotp = newotp;
                    }
                });
            }
        });


        InputOtp();


    }

    private void UserCurrentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(userregisterverificationActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                new CancellationToken() {
                    @NonNull
                    @Override
                    public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                        return null;
                    }

                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                lattitude=location.getLatitude();
                longitude=location.getLongitude();

                Geocoder geocoder = new Geocoder(userregisterverificationActivity.this);

                try {
                    List<Address> addresses = geocoder.getFromLocation(lattitude,longitude,1);
                    address=addresses.get(0).getAddressLine(0);
                    Shopkeeperregister(lattitude,longitude,address);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(userregisterverificationActivity.this, "Error:"+e.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void Shopkeeperregister(double lattitude,double longitude,String address) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params  = new RequestParams();

        params.put("name",strname);
        params.put("emailid",stremailid);
        params.put("mobileno",strmobileno);
        params.put("username",strusername);
        params.put("userpassword",strpassword);
        params.put("lattitude",lattitude);
        params.put("longitude",longitude);
        params.put("address",address);

        client.post(urls.userregisterapi,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        Intent i = new Intent(userregisterverificationActivity.this, loginforcustomerActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(userregisterverificationActivity.this, "Data Already Exist", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(userregisterverificationActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InputOtp() {
        etinput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    etinput2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }); etinput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    etinput3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }); etinput3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    etinput4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }); etinput4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    etinput5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }); etinput5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    etinput6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }




}