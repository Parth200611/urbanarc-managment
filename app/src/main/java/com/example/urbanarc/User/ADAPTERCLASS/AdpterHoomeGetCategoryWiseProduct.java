package com.example.urbanarc.User.ADAPTERCLASS;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.example.urbanarc.R;
import com.example.urbanarc.User.POJOCLASS.POJOgetCateggroryWisePoduct;
import com.example.urbanarc.User.userhomepagegetCategorywiseproductdetail;
import com.example.urbanarc.comman.urls;

import java.util.List;

public class AdpterHoomeGetCategoryWiseProduct extends BaseAdapter {

    List<POJOgetCateggroryWisePoduct> pojOgetCateggroryWisePoducts;
    Activity activity;

    public AdpterHoomeGetCategoryWiseProduct(List<POJOgetCateggroryWisePoduct> pojOgetCateggroryWisePoducts, Activity activity) {
        this.pojOgetCateggroryWisePoducts = pojOgetCateggroryWisePoducts;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojOgetCateggroryWisePoducts.size();
    }

    @Override
    public Object getItem(int position) {
        return pojOgetCateggroryWisePoducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Viewholder itemView;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            itemView = new Viewholder();
            convertView=inflater.inflate(R.layout.rvgetcategorywiseproduct,null);
            itemView.cvcard=convertView.findViewById(R.id.CvrvUsercategorywiseproductcard);
            itemView.ivproductimage=convertView.findViewById(R.id.rvUserCategoryWiseProsuct);
            itemView.tvproductname = convertView.findViewById(R.id.tvrvUserCategoryProductName);
            itemView.tvprice=convertView.findViewById(R.id.rvUserFragmentCategoryProductPrice);
            itemView.tvrating=convertView.findViewById(R.id.rvUserFragmentCategoryProductRating);
            itemView.tvoffer=convertView.findViewById(R.id.rvUserCategoryProductoffer);
            itemView.tvdelivery=convertView.findViewById(R.id.rvUserFragmentCategoryProductDelivery);
            convertView.setTag(itemView);
        }else {
            itemView=(Viewholder) convertView.getTag();
        }
        final  POJOgetCateggroryWisePoduct obj = pojOgetCateggroryWisePoducts.get(position);
        itemView.tvproductname.setText(obj.getProductname());
        itemView.tvprice.setText(obj.getPrice());
        itemView.tvrating.setText(obj.getRating());
        itemView.tvoffer.setText(obj.getOffer());
        itemView.tvdelivery.setText(obj.getDelivery());
        Glide.with(activity)
                .load(urls.address+"images/"+obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)
                .into(itemView.ivproductimage);
        itemView.cvcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, userhomepagegetCategorywiseproductdetail.class);
                i.putExtra("id",obj.getId());
                activity.startActivity(i);

            }
        });


        return convertView;
    }


}
class  Viewholder{
    CardView cvcard;
    ImageView ivproductimage;
    TextView tvproductname,tvprice,tvrating,tvoffer,tvdelivery;
}