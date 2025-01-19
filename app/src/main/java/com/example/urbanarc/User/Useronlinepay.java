package com.example.urbanarc.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Useronlinepay extends AppCompatActivity {
    TextView tvnameofuse,tvmobileno,tvaddress;
    TextView tvproductname,tvprice,tvoffer,tvdelivery,tvfinalamount;
    ImageView ivproductimage;
    TextView tvcashondelivery,tvonline,tvcard,tvupiid;
    AppCompatButton btnconfirmorder;
    Button copyuip;
    String strusername,strimage,strproductname,strprice,stroffer,strdiscription,strrating,strdelivery,strshopname,strcategory,strproductid;
    String strgetNameofuser,strgetMobilenoofuser,strgetUseraddress;
    String strlattitude,strlongitude,straddress;
    String selectedPaymentMethod;
    Button btndone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_useronlinepay);
        getWindow().setNavigationBarColor(ContextCompat.getColor(Useronlinepay.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(Useronlinepay.this,R.color.green));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        strusername = getIntent().getStringExtra("username");
        strimage = getIntent().getStringExtra("image");
        strproductname = getIntent().getStringExtra("productname");
        strprice = getIntent().getStringExtra("price");
        stroffer = getIntent().getStringExtra("offer");
        strdiscription = getIntent().getStringExtra("discription");
        strrating = getIntent().getStringExtra("rating");
        strdelivery = getIntent().getStringExtra("deliveryday");
        strshopname = getIntent().getStringExtra("shopname");
        strcategory = getIntent().getStringExtra("category");
        strproductid = getIntent().getStringExtra("productid");

        strgetNameofuser = getIntent().getStringExtra("userofname");
        strgetMobilenoofuser = getIntent().getStringExtra("usermobileno");
        strgetUseraddress = getIntent().getStringExtra("useraddress");
        strlattitude = getIntent().getStringExtra("lattitude");
        strlongitude = getIntent().getStringExtra("longitude");
        selectedPaymentMethod=getIntent().getStringExtra("paymentmethode");

        btndone=findViewById(R.id.btnPaymentdone);

        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // OrderConfirm();
            }
        });
    }

    private void OrderConfirm() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        // Adding the same parameters as in the PHP script
        params.put("username", strusername);
        params.put("name", strgetNameofuser);
        params.put("mobileno", strgetMobilenoofuser);
        params.put("lattitude", strlattitude);
        params.put("longitude", strlongitude);
        params.put("address", strgetUseraddress); // Ensure this matches the PHP key "address"
        params.put("image", strimage);
        params.put("productname", strproductname);
        params.put("price", strprice);
        params.put("offer", stroffer);
        params.put("discription", strdiscription); // Typo might exist, should be "description" if needed
        params.put("delivery", strdelivery); // Ensure this aligns with the PHP "delieryday" column
        params.put("shopname", strshopname);
        params.put("category", strcategory);
        params.put("productid", strproductid);
        params.put("payment", selectedPaymentMethod);

        client.post(urls.orderbill, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")) {
                        Intent i = new Intent(Useronlinepay.this, Confirmorder.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(Useronlinepay.this, "Error: Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("JSON Error", "Failed to parse response: " + e.getMessage());
                    Toast.makeText(Useronlinepay.this, "Error parsing response.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("Error", "Raw Response: " + responseString, throwable);
                Toast.makeText(Useronlinepay.this, "google pay server not working", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
