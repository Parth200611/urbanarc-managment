package com.example.urbanarc.User.ADAPTERCLASS;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.urbanarc.R;
import com.example.urbanarc.User.POJOCLASS.POJOUserFragmentHomepagePlantData;
import com.example.urbanarc.comman.urls;

import java.util.List;

public class AdapterUserHomeFragmentPlantData extends RecyclerView.Adapter<AdapterUserHomeFragmentPlantData.ViewHolder> {
    List<POJOUserFragmentHomepagePlantData> pojoUserFragmentHomepagePlantData;
    Activity activity;

    public AdapterUserHomeFragmentPlantData(List<POJOUserFragmentHomepagePlantData> pojoUserFragmentHomepagePlantData, Activity activity) {
        this.pojoUserFragmentHomepagePlantData = pojoUserFragmentHomepagePlantData;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterUserHomeFragmentPlantData.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rvuserfragment_category1,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUserHomeFragmentPlantData.ViewHolder holder, int position) {
        POJOUserFragmentHomepagePlantData obj = pojoUserFragmentHomepagePlantData.get(position);
        holder.tvproductname.setText(obj.getProductname());
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
        return pojoUserFragmentHomepagePlantData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivimage;
        TextView tvproductname,tvprice,tvrating,tvoffer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivimage = itemView.findViewById(R.id.rvUserFragmentCategoryProductImge);
            tvproductname =itemView.findViewById(R.id.rvUserFragmentCategoryProductName);
            tvprice =itemView.findViewById(R.id.rvUserFragmentCategoryProductPrice);
            tvrating =itemView.findViewById(R.id.rvUserFragmentCategoryProductRating);
            tvoffer =itemView.findViewById(R.id.rvUserFragmentCategoryProductoffer);

        }
    }
}
