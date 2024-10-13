package com.example.urbanarc.Admin.ADAPTERCLASS;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.urbanarc.Admin.POJOCLASS.POJOADMINgetallshopkeeperlist;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;

import java.util.List;

public class AdapterAdmingetallshopkeeper extends BaseAdapter {
    List<POJOADMINgetallshopkeeperlist> pojoadmiNgetallshopkeeperlists;
    Activity activity;

    public AdapterAdmingetallshopkeeper(List<POJOADMINgetallshopkeeperlist> pojoadmiNgetallshopkeeperlists, Activity activity) {
        this.pojoadmiNgetallshopkeeperlists = pojoadmiNgetallshopkeeperlists;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojoadmiNgetallshopkeeperlists.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoadmiNgetallshopkeeperlists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.lv_admin_getall_shopkeeperlst_desgin, null);
            viewHolder.cvshopkeeper = convertView.findViewById(R.id.cvlvadmingetallshopkeepercardview);
            viewHolder.ivshopkeeperimage = convertView.findViewById(R.id.ivlvadmingetallshopkeeperimnage);
            viewHolder.tvshopname = convertView.findViewById(R.id.tvlvgetallshopkeerperdataname);
            viewHolder.tvusername = convertView.findViewById(R.id.tvlvgetallshopkeeperdatausernamename);
            viewHolder.tvemailid = convertView.findViewById(R.id.tvlvgetallshopkeeperdataemailid);
            viewHolder.tvmobileno = convertView.findViewById(R.id.tvlvgetallshopkeeperdatamobileno);
            viewHolder.tvpassword = convertView.findViewById(R.id.tvlvgetallshopkeeperdatauserpassword);
            viewHolder.tvaddress = convertView.findViewById(R.id.tvlvgetallshopkeeperdatauseraddress);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        POJOADMINgetallshopkeeperlist obj = pojoadmiNgetallshopkeeperlists.get(position);
        viewHolder.tvshopname.setText(obj.getName());
        viewHolder.tvusername.setText(obj.getUsername());
        viewHolder.tvemailid.setText(obj.getEmailid());
        viewHolder.tvmobileno.setText(obj.getMobileno());
        viewHolder.tvpassword.setText(obj.getPassword());
        viewHolder.tvaddress.setText(obj.getAddrss());
        Glide.with(activity)
                .load(urls.address + "images/" + obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(viewHolder.ivshopkeeperimage);


        return convertView;
    }


    class ViewHolder {
        CardView cvshopkeeper;
        ImageView ivshopkeeperimage;
        TextView tvshopname, tvusername, tvemailid, tvmobileno, tvpassword, tvaddress;
    }

}








