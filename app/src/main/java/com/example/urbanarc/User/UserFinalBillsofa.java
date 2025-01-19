package com.example.urbanarc.User;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class UserFinalBillsofa extends AppCompatActivity {

    TextView tvnameofuse,tvmobileno,tvaddress;
    TextView tvproductname,tvprice,tvoffer,tvdelivery,tvfinalamount;
    ImageView ivproductimage;
    TextView tvcashondelivery,tvonline,tvcard,tvupiid;
    AppCompatButton btnconfirmorder;
    Button copyuip;
    String strusername,strimage,strproductname,strprice,stroffer,strdiscription,strrating,strdelivery,strshopname,strcategory,strproductid;
    String strgetNameofuser,strgetMobilenoofuser,strgetUseraddress;
    String strlattitude,strlongitude,straddress;
     String selectedPaymentMethod =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_final_billsofa);
        getWindow().setStatusBarColor(ContextCompat.getColor(UserFinalBillsofa.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(UserFinalBillsofa.this,R.color.white));
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

        tvnameofuse = findViewById(R.id.tvUserNameofuser);
        tvmobileno = findViewById(R.id.tvUserMobileno);
        tvaddress = findViewById(R.id.tvuserAddress);
        tvproductname = findViewById(R.id.tvproductName);
        tvprice = findViewById(R.id.tvproductAmount);
        tvoffer = findViewById(R.id.tvproductOffer);
        tvdelivery = findViewById(R.id.tvproductDelivery);
        tvfinalamount=findViewById(R.id.tvfinalAmount);
        ivproductimage=findViewById(R.id.ivproductImage);
        tvcashondelivery=findViewById(R.id.tvcashOnDelivery);
        tvonline=findViewById(R.id.tvOnlineOnDelivery);
        tvcard=findViewById(R.id.tvpaybycard);
        btnconfirmorder=findViewById(R.id.confirmOrderButton);
        tvupiid = findViewById(R.id.tvUpiId);
        copyuip = findViewById(R.id.btnCopyUpi);

        //user
        tvnameofuse.setText(strgetNameofuser);
        tvmobileno.setText(strgetMobilenoofuser);
        tvaddress.setText(strgetUseraddress);
        //product
        tvproductname.setText(strproductname);
        tvprice.setText(strprice);
        tvoffer.setText(stroffer);
        tvdelivery.setText(strdelivery);
        tvfinalamount.setText(strprice);
        Glide.with(UserFinalBillsofa.this)
                .load(urls.address+"images/"+strimage)
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(ivproductimage);


        // Cash on Delivery selection
        tvcashondelivery.setOnClickListener(v -> {
            selectedPaymentMethod = "Cash on Delivery";
            updateSelection(tvcashondelivery);
            tvupiid.setVisibility(View.GONE); // Hide UPI ID when Cash on Delivery is selected
        });

        // Online Payment selection
        tvonline.setOnClickListener(v -> {
            Intent i = new Intent(UserFinalBillsofa.this,Useronlinepay.class);
            selectedPaymentMethod = "Online Payment";
            i.putExtra("username",strusername);
            i.putExtra("image",strimage);
            i.putExtra("productname",strproductname);
            i.putExtra("price",strprice);
            i.putExtra("offer",stroffer);
            i.putExtra("discription",strdiscription);
            i.putExtra("rating",strrating);
            i.putExtra("shopname",strshopname);
            i.putExtra("deliveryday",strdelivery);
            i.putExtra("category",strcategory);
            i.putExtra("productid",strproductid);
            i.putExtra("userofname",strgetNameofuser);
            i.putExtra("usermobileno",strgetMobilenoofuser);
            i.putExtra("useraddress",straddress);
            i.putExtra("lattitude",strlattitude);
            i.putExtra("longitude",strlongitude);
            i.putExtra("paymentmethode",strlongitude);
            startActivity(i);



//            updateSelection(tvonline);
//            onlinepay();
        });

        // Card Payment selection
        tvcard.setOnClickListener(v -> {
            selectedPaymentMethod = "Card Payment";
            updateSelection(tvcard);


            tvupiid.setVisibility(View.GONE); // Hide UPI ID when Card Payment is selected
        });

        btnconfirmorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPaymentMethod == null){
                    Toast.makeText(UserFinalBillsofa.this, "select Payment Way", Toast.LENGTH_SHORT).show();
                }else {
                    OrderConfirm();
                }
            }
        });


    }

    private void onlinepay() {
        String upiId = "parthdahapute7@okaxis";  // Replace with the recipient's UPI ID
        String amount = "100.00";  // Amount to be paid
        String note = "Payment for Order";  // Transaction note

        // Construct the UPI URI
        Uri uri = Uri.parse("upi://pay")
                .buildUpon()
                .appendQueryParameter("pa", upiId)  // Payee UPI ID
                .appendQueryParameter("pn", "Recipient Name")  // Payee Name (Optional)
                .appendQueryParameter("tn", note)  // Transaction Note
                .appendQueryParameter("am", amount)  // Amount
                .appendQueryParameter("cu", "INR")  // Currency
                .build();

        // Create an intent for UPI payment
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);

        // Debugging log for intent URI
        Log.d("UPI_INTENT", "Intent URI: " + uri.toString());

        // Check if there is any UPI app that can handle this intent
        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(intent, 0);

        if (!resolveInfoList.isEmpty()) {
            // If UPI apps are available, launch the intent
            startActivity(intent);
        } else {
            // No UPI apps found
            Toast.makeText(this, "No UPI app found. Please install a UPI-enabled app.", Toast.LENGTH_SHORT).show();
        }
    }


    public void updateSelection(TextView selectedView) {
        // Reset all colors to black
        tvcashondelivery.setTextColor(Color.BLACK);
        tvonline.setTextColor(Color.BLACK);
        tvcard.setTextColor(Color.BLACK);

        // Set selected color to blue for chosen payment method
        selectedView.setTextColor(Color.BLUE);
    }
    private void OrderConfirm() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strusername);
        params.put("name",strgetNameofuser);
        params.put("mobileno",strgetMobilenoofuser);
        params.put("lattitude",strlattitude);
        params.put("longitude",strlongitude);
        params.put("address",strgetUseraddress);
        params.put("image",strimage);
        params.put("productname",strproductname);
        params.put("price",strprice);
        params.put("offer",stroffer);
        params.put("discription",strdiscription);
        params.put("delivery",strdelivery);
        params.put("shopname",strshopname);
        params.put("category",strcategory);
        params.put("productid",strproductid);
        params.put("payment",selectedPaymentMethod);

        client.post(urls.orderbill,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        Intent i = new Intent(UserFinalBillsofa.this,Confirmorder.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(UserFinalBillsofa.this, "Error try after Sometime", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(UserFinalBillsofa.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}








