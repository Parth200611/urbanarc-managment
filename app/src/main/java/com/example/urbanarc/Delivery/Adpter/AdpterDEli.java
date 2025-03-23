package com.example.urbanarc.Delivery.Adpter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.urbanarc.Delivery.POJO.POJODELI;
import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterDEli extends RecyclerView.Adapter<AdpterDEli.ViewHolder> {
    List<POJODELI> pojodeliList;
    Activity activity;
    String image,productname,address,price,username,payment,delusername,id;

    public AdpterDEli(List<POJODELI> pojodeliList, Activity activity) {
        this.pojodeliList = pojodeliList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterDEli.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.delivery,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterDEli.ViewHolder holder, int position) {

            POJODELI product = pojodeliList.get(position);

            // Set product details
            holder.tvProductname.setText(product.getProductname());
            holder.tvCategory.setText(product.getCategory());
            holder.tvPrice.setText("₹" + product.getPrice());
            holder.tvAddress.setText(product.getAddress());
            holder.tvRating.setText(product.getDeliveryday());
            holder.tvDis.setText(product.getDescription());
            holder.tvName.setText(product.getName());
            holder.tvPayment.setText(product.getPayment());
        Glide.with(activity)
                .load(urls.address + "images/"+product.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.cvImage);

        holder.btnCalluser.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + product.getMobileno()));
            activity.startActivity(intent);
        });

        image=product.getLongitude();
        productname=product.getProductname();
        price=product.getPrice();
        username=product.getName();
        address=product.getAddress();
        payment=product.getPayment();
        id=product.getId();
        delusername=product.getLatitude();

        holder.btnRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHis();
            }
        });

    }

    private void AddHis() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("image",image);
        params.put("productname",productname);
        params.put("price",price);
        params.put("username",username);
        params.put("address",address);
        params.put("payment",payment);
        params.put("deluser",delusername);
        client.post(urls.hisAdd,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String statu=response.getString("success");
                    if (statu.equals("1")){
                        Toast.makeText(activity, "Thank You For Delivering safely", Toast.LENGTH_SHORT).show();
                        RemoveProduct(id);
                    }else {
                        Toast.makeText(activity, "Fail to add", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void RemoveProduct(String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id", id);

        client.post(urls.delFinal, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getString("status").equals("success")) {
                        Toast.makeText(activity, "Order Add To History", Toast.LENGTH_SHORT).show();

                        // Find and remove the item from the list
                        for (int i = 0; i < pojodeliList.size(); i++) {
                            if (pojodeliList.get(i).getId().equals(id)) {
                                pojodeliList.remove(i);
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i, pojodeliList.size());
                                break;
                            }
                        }
                    } else {
                        Toast.makeText(activity, "Failed to Remove Product", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(activity, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojodeliList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cvImage;
        TextView tvProductname, tvCategory, tvPrice, tvAddress, tvRating, tvDis, tvName, tvPayment;
        AppCompatButton btnCalluser, btnRemoveProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvImage = itemView.findViewById(R.id.cvImage);
            tvProductname = itemView.findViewById(R.id.tvProductname);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvDis = itemView.findViewById(R.id.tvDis);
            tvName = itemView.findViewById(R.id.tvName);
            tvPayment = itemView.findViewById(R.id.tvPayment);
            btnCalluser = itemView.findViewById(R.id.btnCalluser);
            btnRemoveProduct = itemView.findViewById(R.id.btnRemmoveProduct);
        }
    }
}
