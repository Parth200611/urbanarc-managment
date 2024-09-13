package com.example.urbanarc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class shopkeeperpasswordupdateActivity extends AppCompatActivity {
    EditText etpassword,etconfirmpassword;
    Button btnnext;
    ProgressDialog progressDialog;
    String strmobileno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeperpasswordupdate);
        getWindow().setNavigationBarColor(ContextCompat.getColor(shopkeeperpasswordupdateActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(shopkeeperpasswordupdateActivity.this,R.color.green));
        etpassword = findViewById(R.id.etShopkeeperupdatepasswordnewpassword);
        etconfirmpassword = findViewById(R.id.etShopkeeperupdatepasswordConfirmpassword);
        btnnext = findViewById(R.id.btnShopkeeperupdatepasswordnext);

        strmobileno = getIntent().getStringExtra("mobileno");

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etpassword.getText().toString().isEmpty()) {
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
                    progressDialog = new ProgressDialog(shopkeeperpasswordupdateActivity.this);
                    progressDialog.setTitle("Loading");
                    progressDialog.setMessage("Please Wait");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + strmobileno, 60, TimeUnit.SECONDS, shopkeeperpasswordupdateActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            Toast.makeText(shopkeeperpasswordupdateActivity.this, "verification Done", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(shopkeeperpasswordupdateActivity.this,"verification Fail",Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(otp, forceResendingToken);
                            Intent i = new Intent(shopkeeperpasswordupdateActivity.this, shopkeeperpasswordupdationverificationActivity.class);
                            i.putExtra("otp",otp);
                            i.putExtra("mobileno",strmobileno);
                            i.putExtra("password",etpassword.getText().toString());
                            startActivity(i);
                        }
                    });


                }
            }

        });
    }
}