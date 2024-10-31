package com.example.urbanarc.User.ADAPTERCLASS;

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
import com.example.urbanarc.R;
import com.example.urbanarc.User.POJOCLASS.POJOUserGetAddtoCartdata;
import com.example.urbanarc.User.Useraddtocartdetails;
import com.example.urbanarc.comman.urls;

import java.util.List;

public class AdpterUserAddtocartdata extends RecyclerView.Adapter<AdpterUserAddtocartdata.ViewHolder> {

    List<POJOUserGetAddtoCartdata> pojoUserGetAddtoCartdata;
    Activity activity;

    public AdpterUserAddtocartdata(List<POJOUserGetAddtoCartdata> pojoUserGetAddtoCartdata, Activity activity) {
        this.pojoUserGetAddtoCartdata = pojoUserGetAddtoCartdata;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterUserAddtocartdata.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rvwishlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterUserAddtocartdata.ViewHolder holder, int position) {
        POJOUserGetAddtoCartdata obj=pojoUserGetAddtoCartdata.get(position);
        holder.tvproductname.setText(obj.getProductname());
        holder.tvprice.setText(obj.getPrice());
        holder.tvrating.setText(obj.getRating());
        holder.tvoffer.setText(obj.getOffer());

        Glide.with(activity)
                .load(urls.address + "images/"+obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.ivimage);

        holder.cvCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, Useraddtocartdetails.class);
                i.putExtra("id",obj.getId());
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pojoUserGetAddtoCartdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivimage;
        TextView tvproductname,tvprice,tvrating,tvoffer;
        CardView cvCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivimage = itemView.findViewById(R.id.rvUserFragmentCategoryProductImge);
            tvproductname =itemView.findViewById(R.id.rvUserFragmentCategoryProductName);
            tvprice =itemView.findViewById(R.id.rvUserFragmentCategoryProductPrice);
            tvrating =itemView.findViewById(R.id.rvUserFragmentCategoryProductRating);
            tvoffer =itemView.findViewById(R.id.rvUserFragmentCategoryProductoffer);
            cvCard =itemView.findViewById(R.id.rvUserFragmentCategoryProductCard);
        }
    }
}
