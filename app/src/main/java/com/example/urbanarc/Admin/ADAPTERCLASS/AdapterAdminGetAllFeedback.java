package com.example.urbanarc.Admin.ADAPTERCLASS;

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
import com.example.urbanarc.Admin.POJOCLASS.POJOAdmingetAllFeedback;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;

import java.util.List;

public class AdapterAdminGetAllFeedback extends RecyclerView.Adapter<AdapterAdminGetAllFeedback.ViewHolde>{
    List<POJOAdmingetAllFeedback> pojoAdmingetAllFeedbacks;
    Activity activity;

    public AdapterAdminGetAllFeedback(List<POJOAdmingetAllFeedback> pojoAdmingetAllFeedbacks, Activity activity) {
        this.pojoAdmingetAllFeedbacks = pojoAdmingetAllFeedbacks;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterAdminGetAllFeedback.ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rv_admin_allfeedback_desgain,parent,false);
        return new ViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdminGetAllFeedback.ViewHolde holder, int position) {
        POJOAdmingetAllFeedback obj = pojoAdmingetAllFeedbacks.get(position);
        holder.tvusername.setText(obj.getUsername());
        holder.tvdate.setText(obj.getDate());
        holder.tvtime.setText(obj.getTime());
        holder.tvmessage.setText(obj.getMessage());

        Glide.with(activity)
                .load(urls.address + "images/"+obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.ivuserimage);


    }

    @Override
    public int getItemCount() {
        return pojoAdmingetAllFeedbacks.size();
    }

    public class ViewHolde extends RecyclerView.ViewHolder {

        TextView tvusername,tvdate,tvtime,tvmessage;
        CardView cvcard;
        ImageView ivuserimage;
        public ViewHolde(@NonNull View view) {
            super(view);
            tvusername=view.findViewById(R.id.rvAdmingetallfeedbavckusername);
            tvdate=view.findViewById(R.id.rvAdmingetallfeedbavckdate);
            tvtime=view.findViewById(R.id.rvAdmingetallfeedbavcktime);
            tvmessage=view.findViewById(R.id.rvAdmingetallfeedbavcktmessage);
            cvcard=view.findViewById(R.id.cvrvAdmingetallfeedbavckcard);
            ivuserimage=view.findViewById(R.id.ivrvAdmingetallfeedbackuserimage);
        }
    }
}
