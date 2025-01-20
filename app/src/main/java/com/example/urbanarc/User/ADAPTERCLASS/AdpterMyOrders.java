package com.example.urbanarc.User.ADAPTERCLASS;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.urbanarc.R;
import com.example.urbanarc.User.MYordersdetails;
import com.example.urbanarc.User.POJOCLASS.POJOMYORDERS;
import com.example.urbanarc.comman.urls;

import java.util.List;

public class AdpterMyOrders extends RecyclerView.Adapter<AdpterMyOrders.ViewhOLDER> {
    List<POJOMYORDERS> pojomyorders;
    Activity activity;

    public AdpterMyOrders(List<POJOMYORDERS> pojomyorders, Activity activity) {
        this.pojomyorders = pojomyorders;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterMyOrders.ViewhOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.myorders,parent,false);
        return new ViewhOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterMyOrders.ViewhOLDER holder, int position) {
        POJOMYORDERS obj=pojomyorders.get(position);
        holder.tvname.setText(obj.getName());
        Glide.with(activity)
                .load(urls.address + "images/"+obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.ivimage);

        holder.cvcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity, MYordersdetails.class);
                i.putExtra("productid",obj.getProductid());
                Log.d("ProductIDCheck", "Product ID: " + obj.getProductid()); // Log the product ID
                activity.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return pojomyorders.size();
    }

    public class ViewhOLDER extends RecyclerView.ViewHolder {
        ImageView ivimage;
        TextView tvname;
        CardView cvcard;
        public ViewhOLDER(@NonNull View itemView) {
            super(itemView);
            ivimage=itemView.findViewById(R.id.orderimage);
            tvname=itemView.findViewById(R.id.ordername);
            cvcard=itemView.findViewById(R.id.ordercard);
        }
    }
}
