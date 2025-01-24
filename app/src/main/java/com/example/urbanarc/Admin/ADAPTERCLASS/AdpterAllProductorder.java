package com.example.urbanarc.Admin.ADAPTERCLASS;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.urbanarc.Admin.OrderDEtails;
import com.example.urbanarc.Admin.POJOCLASS.POJOALLOder;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;

import java.util.List;

public class AdpterAllProductorder extends RecyclerView.Adapter<AdpterAllProductorder.ViewHolder> {
    List<POJOALLOder> pojoallOders;
    Activity activity;

    public AdpterAllProductorder(List<POJOALLOder> pojoallOders, Activity activity) {
        this.pojoallOders = pojoallOders;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterAllProductorder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.allordersdesgin,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterAllProductorder.ViewHolder holder, int position) {
        POJOALLOder obj=pojoallOders.get(position);
        holder.productname.setText(obj.getProductname());

        holder.shop.setText(obj.getShopname());
        holder.category.setText(obj.getCategory());
        holder.username.setText(obj.getUsername());
        holder.payment.setText(obj.getPayment());
        holder.delivery.setText(obj.getDelivery());
        holder.offer.setText(obj.getOffer());

        Glide.with(activity)
                .load(urls.address + "images/"+obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.ivimage);

        holder.cvcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, OrderDEtails.class);
                i.putExtra("id",obj.getId());
                activity.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return pojoallOders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvcard;
        ImageView ivimage;
        TextView productname,rating,shop,category,username,payment,offer,delivery;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvcard=itemView.findViewById(R.id.cardview);
            ivimage=itemView.findViewById(R.id.product_image);
            productname=itemView.findViewById(R.id.product_name);

            shop=itemView.findViewById(R.id.shop_name);
            category=itemView.findViewById(R.id.product_category);
            username=itemView.findViewById(R.id.username);
            payment=itemView.findViewById(R.id.payment_details);
            delivery=itemView.findViewById(R.id.delivery_status);
            offer=itemView.findViewById(R.id.offer_details);
        }
    }
}
