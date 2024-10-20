package com.example.urbanarc.User.ADAPTERCLASS;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.urbanarc.R;
import com.example.urbanarc.User.POJOUserFragmentCategory;
import com.example.urbanarc.comman.urls;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterUserHomefeagmentcategory extends RecyclerView.Adapter<AdpterUserHomefeagmentcategory.Viewholder> {

    List<POJOUserFragmentCategory> pojoUserFragmentCategories;
    Activity activity;

    public AdpterUserHomefeagmentcategory(List<POJOUserFragmentCategory> pojoUserFragmentCategories, Activity activity) {
        this.pojoUserFragmentCategories = pojoUserFragmentCategories;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterUserHomefeagmentcategory.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rv_userhomefragment_allcategorydesgin,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterUserHomefeagmentcategory.Viewholder holder, int position) {
        POJOUserFragmentCategory obj = pojoUserFragmentCategories.get(position);
        holder.tvcategoryname.setText(obj.getCategoryname());
        Glide.with(activity)
                .load(urls.address + "images/"+obj.getCategoryimage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.cvcategoryimage);


    }

    @Override
    public int getItemCount() {
        return pojoUserFragmentCategories.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        CircleImageView cvcategoryimage;
        TextView tvcategoryname;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            cvcategoryimage = itemView.findViewById(R.id.civrvUserHomeFragmentCategoryImage);
            tvcategoryname = itemView.findViewById(R.id.tvrvUserHomeFragmentCategoryname);

        }
    }
}
