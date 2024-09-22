package com.example.urbanarc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactuspageActivity extends AppCompatActivity {
    CardView cvemail,cvwhatsapp,cvcall;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contactuspage);

        cvemail = findViewById(R.id.cvContactusEmail);
        cvwhatsapp = findViewById(R.id.cvContactusWhatsapp);
        cvcall = findViewById(R.id.cvContactusCall);

        cvemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"URBANARC@gmail.com"}); // Replace with company email
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry from the  URBANARC App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, I have a question regarding...");

                // Check if an email client is available
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(emailIntent, "Send email via..."));
                }
            }
        });

        cvwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "+919322766871";
                String message = "Hello, I have a question regarding the product from URBANARC.";

                Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
                whatsappIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message)));

                // Check if WhatsApp is installed
                if (whatsappIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(whatsappIntent);
                } else {
                    Toast.makeText(ContactuspageActivity.this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        cvcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "+917020472240"; // Replace with your company phone number

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));

                // Check if the device can handle a call intent
                if (callIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(callIntent);
                } else {
                    Toast.makeText(ContactuspageActivity.this, "Phone app is not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}