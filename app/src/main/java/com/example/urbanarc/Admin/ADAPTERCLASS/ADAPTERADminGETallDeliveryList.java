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
import com.example.urbanarc.Admin.POJOCLASS.POJOADminGetALLDELIVERYLIst;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;

import java.util.List;

public class ADAPTERADminGETallDeliveryList extends BaseAdapter {
    List<POJOADminGetALLDELIVERYLIst> pojoaDminGetALLDELIVERYLIsts;
    Activity activity;

    public ADAPTERADminGETallDeliveryList(List<POJOADminGetALLDELIVERYLIst> pojoaDminGetALLDELIVERYLIsts, Activity activity) {
        this.pojoaDminGetALLDELIVERYLIsts = pojoaDminGetALLDELIVERYLIsts;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojoaDminGetALLDELIVERYLIsts.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoaDminGetALLDELIVERYLIsts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            holder = new ViewHolder();
            convertView=inflater.inflate(R.layout.lv_admin_getall_delivereyboydata,null);
            holder.cvDeliverydatacard=convertView.findViewById(R.id.cvlvadmingetalldeliverycardview);
            holder.ivprofilimage=convertView.findViewById(R.id.ivlvadmingetalldeliveryimnage);
            holder.tvname=convertView.findViewById(R.id.tvlvgetalldeliverydataname);
            holder.tvusername=convertView.findViewById(R.id.tvlvgetalldeliverydatausernamename);
            holder.tvemailid=convertView.findViewById(R.id.tvlvgetalldeliverydataemailid);
            holder.tvmobileno=convertView.findViewById(R.id.tvlvgetalldeliverydatamobileno);
            holder.tvpassword=convertView.findViewById(R.id.tvlvgetalldeliverydatauserpassword);
            holder.tvrating=convertView.findViewById(R.id.tvlvgetalldeliverydatauserrating);
            holder.tvorders=convertView.findViewById(R.id.tvlvgetalldeliverydatauserorders);

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        POJOADminGetALLDELIVERYLIst obj=pojoaDminGetALLDELIVERYLIsts.get(position);
        holder.tvname.setText(obj.getName());
        holder.tvusername.setText(obj.getUsername());
        holder.tvrating.setText(obj.getRating());
        holder.tvemailid.setText(obj.getEmailid());
        holder.tvmobileno.setText(obj.getMobileno());
        holder.tvpassword.setText(obj.getPassword());
        holder.tvorders.setText(obj.getOrders());

        Glide.with(activity)
                .load(urls.address + "images/" +obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.ivprofilimage);


        return convertView;
    }



    class ViewHolder{
        CardView cvDeliverydatacard;
        ImageView ivprofilimage;
        TextView tvname,tvusername,tvrating,tvemailid,tvmobileno,tvpassword,tvorders;
    }

}
