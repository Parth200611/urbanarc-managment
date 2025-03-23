package com.example.urbanarc.Delivery.Adpter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.urbanarc.Delivery.POJO.POJOHis;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterHis extends RecyclerView.Adapter<AdpterHis.viewholder> {
    List<POJOHis>pojoHis;
    Activity activity;

    public AdpterHis(List<POJOHis> pojoHis, Activity activity) {
        this.pojoHis = pojoHis;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterHis.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.his,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterHis.viewholder holder, int position) {
        POJOHis product = pojoHis.get(position);

        // Bind data to views
        holder.tvProductName.setText(product.getProductname());
        holder.tvPrice.setText(product.getPrice());
        holder.tvUsername.setText(product.getUsername());
        holder.tvAddress.setText(product.getAddress());
        holder.tvPayment.setText(product.getPayment());
        Glide.with(activity)
                .load(urls.address + "images/"+product.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.cvImage);


    }

    @Override
    public int getItemCount() {
        return pojoHis.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        CircleImageView cvImage;
        TextView tvProductName, tvPrice, tvUsername, tvAddress, tvPayment;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            cvImage = itemView.findViewById(R.id.cvImage);
            tvProductName = itemView.findViewById(R.id.tvProductname);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPayment = itemView.findViewById(R.id.tvPaymnet);
        }
    }
}
