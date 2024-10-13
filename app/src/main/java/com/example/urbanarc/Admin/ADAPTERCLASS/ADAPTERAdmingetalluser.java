package com.example.urbanarc.Admin.ADAPTERCLASS;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.urbanarc.Admin.AdminUserfulldataActivity;
import com.example.urbanarc.Admin.POJOCLASS.POJOADMINGetallUserData;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;

import java.util.List;

public class ADAPTERAdmingetalluser extends BaseAdapter {
    List<POJOADMINGetallUserData> pojoadminGetallUserData;
    Activity activity;

    public ADAPTERAdmingetalluser(List<POJOADMINGetallUserData> pojoadminGetallUserData, Activity activity) {
        this.pojoadminGetallUserData = pojoadminGetallUserData;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojoadminGetallUserData.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoadminGetallUserData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  Viewholder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            holder=new Viewholder();
            convertView=inflater.inflate(R.layout.lv_admin_aet_all_user,null);
            holder.cvcardviewuserdata=convertView.findViewById(R.id.cvlvadmingetallusercardview);
            holder.ivuserimage=convertView.findViewById(R.id.ivlvadmingetalluserimnage);
            holder.tvname = convertView.findViewById(R.id.tvlvgetalluserdataname);
            holder.tvusername =convertView.findViewById(R.id.tvlvgetalluserdatausernamename);
            holder.tvemailid =convertView.findViewById(R.id.tvlvgetalluserdataemailid);
            holder.tvmobileno=convertView.findViewById(R.id.tvlvgetalluserdatamobileno);
            holder.tvpassword=convertView.findViewById(R.id.tvlvgetalluserdatauserpassword);

            convertView.setTag(holder);
        }else{
            holder=(Viewholder) convertView.getTag();
        }

        final POJOADMINGetallUserData obj =pojoadminGetallUserData.get(position);
        holder.tvname.setText(obj.getName());
        holder.tvusername.setText(obj.getUsername());
        holder.tvemailid.setText(obj.getEmailid());
        holder.tvmobileno.setText(obj.getMobileno());

        holder.tvpassword.setText(obj.getUserpassword());

        Glide.with(activity)
                .load(urls.address+"images/"+obj.getImages())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.ivuserimage);

        holder.cvcardviewuserdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, AdminUserfulldataActivity.class);
                i.putExtra("username",obj.getUsername());
                activity.startActivity(i);
            }
        });

        return convertView;
    }




 class Viewholder{
        CardView cvcardviewuserdata;
        ImageView ivuserimage;
        TextView tvname,tvusername,tvemailid,tvmobileno,tvpassword;
 }

}
