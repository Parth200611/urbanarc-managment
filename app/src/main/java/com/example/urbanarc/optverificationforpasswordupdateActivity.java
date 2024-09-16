package com.example.urbanarc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanarc.comman.urls;
import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class optverificationforpasswordupdateActivity extends AppCompatActivity {

    TextView tvmobileno,tvresendotp;
    EditText etinput1,etinput2,etinput3,etinput4,etinput5,etinput6;
    Button btnnext;
    String strotp,strmobile,strpassword;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_optverificationforpasswordupdate);
        getWindow().setNavigationBarColor(ContextCompat.getColor(optverificationforpasswordupdateActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(optverificationforpasswordupdateActivity.this,R.color.green));

        tvmobileno=findViewById(R.id.tvUserupdatepasswordverificationmobileno);
        tvresendotp=findViewById(R.id.tvUserpassupdateresendotp);
        etinput1=findViewById(R.id.etUserpassupdateationotpinput1);
        etinput2=findViewById(R.id.etUserpassupdateationotpinput2);
        etinput3=findViewById(R.id.etUserpassupdateationotpinput3);
        etinput4=findViewById(R.id.etUserpassupdateationotpinput4);
        etinput5=findViewById(R.id.etUserpassupdateationotpinput5);
        etinput6=findViewById(R.id.etUserpassupdateationotpinput6);
        btnnext=findViewById(R.id.btUserpassupdateverify);

        strotp=getIntent().getStringExtra("otp");
        strmobile=getIntent().getStringExtra("mobileno");
        strpassword=getIntent().getStringExtra("password");

        tvmobileno.setText(strmobile);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etinput1.getText().toString().trim().isEmpty() ||
                        etinput2.getText().toString().trim().isEmpty() ||
                        etinput3.getText().toString().trim().isEmpty() ||
                        etinput4.getText().toString().trim().isEmpty() ||
                        etinput5.getText().toString().trim().isEmpty() ||
                        etinput6.getText().toString().trim().isEmpty()){
                    Toast.makeText(optverificationforpasswordupdateActivity.this,"Enetr Proper Otp",Toast.LENGTH_SHORT).show();
                }
                String otpcode=etinput1.getText().toString()+etinput2.getText().toString()+etinput3.getText().toString()+etinput4.getText().toString()+etinput5.getText().toString()+etinput6.getText().toString();
                if (strotp!=null){
                    progressDialog = new ProgressDialog(optverificationforpasswordupdateActivity.this);
                    progressDialog.setTitle("Verifying");
                    progressDialog.setMessage("Please Wait For Moment");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(strotp,otpcode);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Updatepassword();

                            }else{
                                Toast.makeText(optverificationforpasswordupdateActivity.this, "Updation Fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
        tvresendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + strmobile, 60, TimeUnit.SECONDS, optverificationforpasswordupdateActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String newotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(newotp, forceResendingToken);
                        strotp=newotp;
                    }
                });
            }
        });



        InputOtp();

    }

    private void Updatepassword() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("mobileno",strmobile);
        params.put("userpassword",strpassword);

        client.post(urls.Userpasswordupdate,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        Intent i = new Intent(optverificationforpasswordupdateActivity.this,loginforcustomerActivity.class);
                        startActivity(i);

                    }else{
                        Toast.makeText(optverificationforpasswordupdateActivity.this, "Updation Fail", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(optverificationforpasswordupdateActivity.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InputOtp() {
        etinput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence otp, int start, int before, int count) {
                if(!otp.toString().isEmpty()){
                    etinput2.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etinput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence otp, int start, int before, int count) {
                if(!otp.toString().isEmpty()){
                    etinput3.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etinput3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence otp, int start, int before, int count) {
                if(!otp.toString().isEmpty()){
                    etinput4.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etinput4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence otp, int start, int before, int count) {
                if(!otp.toString().isEmpty()){
                    etinput5.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etinput5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence otp, int start, int before, int count) {
                if(!otp.toString().isEmpty()){
                    etinput6.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}