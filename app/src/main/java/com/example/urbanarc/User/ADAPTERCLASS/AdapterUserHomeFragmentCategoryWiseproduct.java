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
import com.example.urbanarc.User.POJOCLASS.POJOgetCtaegoryWiseproductHomepage;
import com.example.urbanarc.User.UserHomepageProductdetailsSofa;
import com.example.urbanarc.comman.urls;

import java.util.List;

public class AdapterUserHomeFragmentCategoryWiseproduct extends RecyclerView.Adapter<AdapterUserHomeFragmentCategoryWiseproduct.Viewholder> {

    List<POJOgetCtaegoryWiseproductHomepage> pojOgetCtaegoryWiseproductHomepages;
    Activity activity;

    public AdapterUserHomeFragmentCategoryWiseproduct(List<POJOgetCtaegoryWiseproductHomepage> pojOgetCtaegoryWiseproductHomepages, Activity activity) {
        this.pojOgetCtaegoryWiseproductHomepages = pojOgetCtaegoryWiseproductHomepages;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterUserHomeFragmentCategoryWiseproduct.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rvuserfragment_category1,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUserHomeFragmentCategoryWiseproduct.Viewholder holder, int position) {
        POJOgetCtaegoryWiseproductHomepage obj = pojOgetCtaegoryWiseproductHomepages.get(position);
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
                Intent i = new Intent(activity, UserHomepageProductdetailsSofa.class);
                i.putExtra("id",obj.getId());
                activity.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return pojOgetCtaegoryWiseproductHomepages.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView ivimage;
        TextView tvproductname,tvprice,tvrating,tvoffer;
        CardView cvCard;
        public Viewholder(@NonNull View itemView) {
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
