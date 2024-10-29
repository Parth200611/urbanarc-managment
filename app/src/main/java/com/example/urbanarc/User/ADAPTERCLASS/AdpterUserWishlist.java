package com.example.urbanarc.User.ADAPTERCLASS;

import android.app.Activity;
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
import com.example.urbanarc.User.POJOCLASS.POJOUserWishList;
import com.example.urbanarc.comman.urls;

import java.util.List;

public class AdpterUserWishlist extends RecyclerView.Adapter<AdpterUserWishlist.ViewHolder> {

    List<POJOUserWishList> pojoUserWishLists;
    Activity activity;

    public AdpterUserWishlist(List<POJOUserWishList> pojoUserWishLists, Activity activity) {
        this.pojoUserWishLists = pojoUserWishLists;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterUserWishlist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rvwishlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterUserWishlist.ViewHolder holder, int position) {
        POJOUserWishList obj = pojoUserWishLists.get(position);
        holder.tvproductname.setText(obj.getParoductname());
        holder.tvprice.setText(obj.getPrice());
        holder.tvrating.setText(obj.getRating());
        holder.tvoffer.setText(obj.getOffer());
        Glide.with(activity)
                .load(urls.address + "images/"+obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.ivimage);

    }

    @Override
    public int getItemCount() {
        return pojoUserWishLists.size();
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
