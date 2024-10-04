package com.example.urbanarc.User;

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

public class userupdatepasswordActivity extends AppCompatActivity {
    EditText etpassword,etconfirmpassword;
    Button btnnext;

    String strmobileno;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userupdatepassword);
        getWindow().setNavigationBarColor(ContextCompat.getColor(userupdatepasswordActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(userupdatepasswordActivity.this,R.color.green));
        etpassword = findViewById(R.id.etUserupdatepasswordnewpassword);
        etconfirmpassword = findViewById(R.id.etUserupdatepasswordConfirmpassword);
        btnnext = findViewById(R.id.btnUserupdatepasswordnext);

        strmobileno=getIntent().getStringExtra("mobileno");

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
                     progressDialog = new ProgressDialog(userupdatepasswordActivity.this);
                     progressDialog.setTitle("Please Wait");
                     progressDialog.setMessage("Registration is in progress");
                     progressDialog.setCanceledOnTouchOutside(false);
                     progressDialog.show();

                     PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + strmobileno, 60, TimeUnit.SECONDS, userupdatepasswordActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                         @Override
                         public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                             Toast.makeText(userupdatepasswordActivity.this, "Verification Done", Toast.LENGTH_SHORT).show();
                         }

                         @Override
                         public void onVerificationFailed(@NonNull FirebaseException e) {
                             Toast.makeText(userupdatepasswordActivity.this, "Verification Fail", Toast.LENGTH_SHORT).show();
                         }

                         @Override
                         public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                             super.onCodeSent(otp, forceResendingToken);
                             Intent i = new Intent(userupdatepasswordActivity.this, optverificationforpasswordupdateActivity.class);
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