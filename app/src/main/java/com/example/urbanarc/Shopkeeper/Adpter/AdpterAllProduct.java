package com.example.urbanarc.Shopkeeper.Adpter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.urbanarc.R;
import com.example.urbanarc.Shopkeeper.POJO.POJOAllProduct;
import com.example.urbanarc.comman.urls;
import com.google.android.gms.common.api.Api;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterAllProduct extends RecyclerView.Adapter<AdpterAllProduct.ViewHolder> {
    List<POJOAllProduct>pojoAllProducts;
    Activity activity;

    public AdpterAllProduct(List<POJOAllProduct> pojoAllProducts, Activity activity) {
        this.pojoAllProducts = pojoAllProducts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterAllProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(activity).inflate(R.layout.product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterAllProduct.ViewHolder holder, int position) {
        POJOAllProduct product = pojoAllProducts.get(position);

        holder.tvProductName.setText(product.getName());
        holder.tvCategory.setText(product.getCategory());
        holder.tvPrice.setText(product.getPrice());
        holder.tvOffer.setText(product.getOffer());
        holder.tvRating.setText(product.getRating());
        holder.tvDescription.setText(product.getDiscription());
        Glide.with(activity)
                .load(urls.address + "images/"+product.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.cvImage);
        holder.btnRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  id=product.getId();
                RemoveProduct(id);
            }
        });

    }

    private void RemoveProduct(String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id", id);

        client.post(urls.RemoveProductShop, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getString("status").equals("success")) {
                        Toast.makeText(activity, "Product Removed", Toast.LENGTH_SHORT).show();

                        // Find and remove the item from the list
                        for (int i = 0; i < pojoAllProducts.size(); i++) {
                            if (pojoAllProducts.get(i).getId().equals(id)) {
                                pojoAllProducts.remove(i);
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i, pojoAllProducts.size());
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
        return pojoAllProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cvImage;
        TextView tvProductName, tvCategory, tvPrice, tvOffer, tvRating, tvDescription;
        AppCompatButton btnRemoveProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvImage = itemView.findViewById(R.id.cvImage);
            tvProductName = itemView.findViewById(R.id.tvProductname);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOffer = itemView.findViewById(R.id.tvOffer);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvDescription = itemView.findViewById(R.id.tvDis);
            btnRemoveProduct = itemView.findViewById(R.id.btnRemmoveProduct);
        }
    }
}
