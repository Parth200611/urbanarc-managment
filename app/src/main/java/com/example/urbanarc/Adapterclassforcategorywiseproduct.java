package com.example.urbanarc;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.example.urbanarc.comman.urls;

import java.util.List;

public class Adapterclassforcategorywiseproduct extends BaseAdapter {

    List<POJOgetcategorywiseproduct> pojOgetcategorywiseproducts;
    Activity activity;

    public Adapterclassforcategorywiseproduct(List<POJOgetcategorywiseproduct> pojOgetcategorywiseproducts, Activity activity) {
        this.pojOgetcategorywiseproducts = pojOgetcategorywiseproducts;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojOgetcategorywiseproducts.size();
    }

    @Override
    public Object getItem(int position) {
        return pojOgetcategorywiseproducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.lv_category_wise_product_list,null);
            holder.ivproductimmage = convertView.findViewById(R.id.ivcategorywiseproduvtproductimage);
            holder.tvshopname = convertView.findViewById(R.id.tvcategorywiseproductShopname);
            holder.tvrating = convertView.findViewById(R.id.tvcategorywiseproductrating);
            holder.tvproductname = convertView.findViewById(R.id.tvcategorywiseproductproductname);
            holder.tvcategoryname=convertView.findViewById(R.id.tvcategorywiseproductcategory);
            holder.tvproductprice=convertView.findViewById(R.id.tvcategorywiseproductproductnPrice);
            holder.tvproductdiscription = convertView.findViewById(R.id.tvcategorywiseproductproductndiscription);
            holder.tvproductoffer = convertView.findViewById(R.id.tvcategorywiseproductproductnoffer);

            convertView.setTag(holder);

        }else{

            holder = (ViewHolder) convertView.getTag();
        }

        final  POJOgetcategorywiseproduct obj = pojOgetcategorywiseproducts.get(position);
        holder.tvshopname.setText(obj.getCategoryShopname());
        holder.tvrating.setText(obj.getProductrating());
        holder.tvproductname.setText(obj.getCategoryproductname());
        holder.tvcategoryname.setText(obj.getCategoryname());
        holder.tvproductprice.setText(obj.getCategoryproductprice());
        holder.tvproductdiscription.setText(obj.getProductdiscription());
        holder.tvproductoffer.setText(obj.getProductoffer());

        Glide.with(activity)
                .load(urls.address+"images/"+obj.getProductimage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.ivproductimmage);





        return convertView;
    }




    class ViewHolder{
        ImageView ivproductimmage;
        TextView tvshopname,tvrating,tvproductname,tvcategoryname,tvproductprice,tvproductdiscription,tvproductoffer;
    }


}
