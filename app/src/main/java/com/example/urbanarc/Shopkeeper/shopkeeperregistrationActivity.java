package com.example.urbanarc.Shopkeeper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class shopkeeperregistrationActivity extends AppCompatActivity {
    EditText etname,etemailid,etmobileno,etusername,etpassword,etconfirmpassword,etaddress;
    Button btnregisternow;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeperregistration);
        getWindow().setNavigationBarColor(ContextCompat.getColor(shopkeeperregistrationActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(shopkeeperregistrationActivity.this,R.color.white));
        etname = findViewById(R.id.etShopkeeperregistrationName);
        etemailid = findViewById(R.id.etShopkeeperregistrationEmailid);
        etmobileno = findViewById(R.id.etShopkeeperregistrationmobileno);
        etaddress = findViewById(R.id.etShopkeeperregistrationaddress);
        etusername = findViewById(R.id.etShopkeeperregistrationUsername);
        etpassword = findViewById(R.id.etShopkeeperregistrationPassword);
        etconfirmpassword = findViewById(R.id.etshopkeeperregistrationconfirmpassword);
        btnregisternow = findViewById(R.id.btnshopkeeperregistrationbtnregister);

        btnregisternow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etname.getText().toString().isEmpty()) {
                    etname.setError("Enter Your Name");
                } else if (etmobileno.getText().toString().isEmpty()) {
                    etmobileno.setError("Enetr the Mobile Number");
                } else if (etmobileno.getText().toString().length() != 10) {
                    etmobileno.setError("Enter  the proper Mobile Number");
                } else if (!etemailid.getText().toString().contains("@") && !etemailid.getText().toString().contains(".com")) {
                    etemailid.setError("enter Proper Emaiil Id");
                } else if (etaddress.getText().toString().isEmpty()){
                    etaddress.setError("enter address");
                } else if (etusername.getText().toString().isEmpty()) {
                    etusername.setError("Please enter username");
                } else if (etusername.getText().toString().length() < 8) {
                    etusername.setError("username must be atlest 8  character");
                } else if (!etusername.getText().toString().matches(".*[A-Z].*")) {
                    etusername.setError("Please enter atlest One upper case letter");
                } else if (!etusername.getText().toString().matches(".*[a-z].*")) {
                    etusername.setError("Please enter atlest One lower case letter");
                } else if (!etusername.getText().toString().matches(".*[0-9].*")) {
                    etusername.setError("Please enter atlest One number");
                } else if (!etusername.getText().toString().matches(".*[!,@,#,$,%,&,*].*")) {
                    etusername.setError("Please enter atlest One special symbol");
                } else if (etpassword.getText().toString().isEmpty()) {
                    etpassword.setError("Please enter username");
                } else if (etpassword.getText().toString().length() < 8) {
                    etpassword.setError("username must be atlest 8  character");
                } else if (!etpassword.getText().toString().matches(".*[A-Z].*")) {
                    etpassword.setError("Please enter atlest One upper case letter");
                } else if (!etpassword.getText().toString().matches(".*[a-z].*")) {
                    etpassword.setError("Please enter atlest One lower case letter");
                } else if (!etpassword.getText().toString().matches(".*[0-9].*")) {
                    etpassword.setError("Please enter atlest One number");
                } else if (!etpassword.getText().toString().matches(".*[!,@,#,$,%,&,*].*")) {
                    etpassword.setError("Please enter atlest One special symbol");
                } else if (!etpassword.getText().toString().equals(etconfirmpassword.getText().toString())) {
                    etconfirmpassword.setError("password do not match");

                } else {
                    progressDialog = new ProgressDialog(shopkeeperregistrationActivity.this);
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setMessage("Registration is in progress");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + etmobileno.getText().toString(), 60, TimeUnit.SECONDS, shopkeeperregistrationActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    Toast.makeText(shopkeeperregistrationActivity.this, "Verification Done", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(shopkeeperregistrationActivity.this, "Verification Fail", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(otp, forceResendingToken);
                                    Intent i = new Intent(shopkeeperregistrationActivity.this, shopkeeperregisterverificationActivity.class);
                                    i.putExtra("otp",otp);
                                    i.putExtra("name",etname.getText().toString());
                                    i.putExtra("emailid",etemailid.getText().toString());
                                    i.putExtra("mobileno",etmobileno.getText().toString());
                                    i.putExtra("address",etaddress.getText().toString());
                                    i.putExtra("username",etusername.getText().toString());
                                    i.putExtra("password",etpassword.getText().toString());
                                    startActivity(i);
                                }
                            });


                }
            }
        });




    }
}