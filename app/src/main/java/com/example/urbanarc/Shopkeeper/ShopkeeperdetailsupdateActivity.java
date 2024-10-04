package com.example.urbanarc.Shopkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.urbanarc.R;

public class ShopkeeperdetailsupdateActivity extends AppCompatActivity {
    CardView cvemailupdate,cvpasswordupdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopkeeperdetailsupdate);
        getWindow().setStatusBarColor(ContextCompat.getColor(ShopkeeperdetailsupdateActivity.this,R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(ShopkeeperdetailsupdateActivity.this,R.color.white));

        cvemailupdate=findViewById(R.id.cvShopkeeperEditprofilEditemail);
        cvpasswordupdate=findViewById(R.id.cvShopkeeperEditprofilEditpassword);

        cvemailupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShopkeeperdetailsupdateActivity.this,ShopkeeperUsedrnamecheckforemailupdateActivity.class);
                startActivity(i);
            }
        });

        cvpasswordupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShopkeeperdetailsupdateActivity.this,shopkeepernumbercheckActivity.class);
                startActivity(i);
            }
        });

    }
}