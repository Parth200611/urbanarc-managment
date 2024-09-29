package com.example.urbanarc;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.example.urbanarc.comman.urls;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptergetallCategory extends BaseAdapter {

    List<POJOgetallcategory> pojOgetallcategories;
    Activity activity;

    public AdaptergetallCategory(List<POJOgetallcategory> pojOgetallcategories, Activity activity) {
        this.pojOgetallcategories = pojOgetallcategories;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojOgetallcategories.size();
    }

    @Override
    public Object getItem(int position) {
        return pojOgetallcategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Viewholder viewholder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            viewholder = new Viewholder();
            convertView =inflater.inflate(R.layout.lv_getcall_category_desgin_for_adapter,null);
            viewholder.ImageViewCategoryimage = convertView.findViewById(R.id.ivcategoryAdapterClassdesgingImage);
            viewholder.TextViewCategoryname = convertView.findViewById(R.id.tvcategoryadapterclassdesgingText);
            viewholder.cvproductCategory=convertView.findViewById(R.id.cvcategoryAdapterClassdesgincardview);

            convertView.setTag(viewholder);
        }
        else{

            viewholder=(Viewholder) convertView.getTag();

        }
        final POJOgetallcategory obj = pojOgetallcategories.get(position);
        viewholder.TextViewCategoryname.setText(obj.getCategoryName());

        Glide.with(activity)
                .load(urls.address+"images/"+obj.getCategoryImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)
                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                .override(800, 800) // Resize the image to 800x800 pixels
                .into(viewholder.ImageViewCategoryimage);

        viewholder.cvproductCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity,GetCattegoryWiseProductActivity.class);
                i.putExtra("categoryname",obj.getCategoryName());
                activity.startActivity(i);
            }
        });


        return convertView;
    }

    class Viewholder{
        CardView cvproductCategory;
        CircleImageView ImageViewCategoryimage;
        TextView TextViewCategoryname;
    }
}
