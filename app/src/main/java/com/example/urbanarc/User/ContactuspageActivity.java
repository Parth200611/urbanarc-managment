package com.example.urbanarc.User;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.urbanarc.R;

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
                String phoneNumber = "+919322766871"; // Indian phone number format
                String message = "Hello, I have a question regarding the product from URBANARC.";

                // Use the WhatsApp API URL to send a message
                String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);

                Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
                whatsappIntent.setData(Uri.parse(url));

                try {
                    // Try to launch WhatsApp (the system will handle if it's regular or Business)
                    startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {
                    // If WhatsApp is not installed, display a toast message
                    Toast.makeText(ContactuspageActivity.this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}