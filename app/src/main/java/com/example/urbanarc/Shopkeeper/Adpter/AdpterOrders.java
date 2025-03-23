package com.example.urbanarc.Shopkeeper.Adpter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.urbanarc.R;
import com.example.urbanarc.Shopkeeper.POJO.POJOOrders;
import com.example.urbanarc.User.Confirmorder;
import com.example.urbanarc.User.UserFinalBillsofa;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterOrders extends RecyclerView.Adapter<AdpterOrders.ViewHolder> {
    List<POJOOrders>pojoOrders;
    Activity activity;
    String id, username, name, mobileno, latitude, longitude, address, image, productname, price, offer, description, deliveryday, shopname, category, productid, payment;

    public AdpterOrders(List<POJOOrders> pojoOrders, Activity activity) {
        this.pojoOrders = pojoOrders;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterOrders.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterOrders.ViewHolder holder, int position) {
        POJOOrders product = pojoOrders.get(position);

        holder.tvProductname.setText(product.getProductname());
        holder.tvCategory.setText(product.getCategory());
        holder.tvPrice.setText("₹" + product.getPrice());
        holder.tvOffer.setText(product.getOffer());
        holder.tvDis.setText(product.getDescription());
        holder.tvName.setText(product.getName());
        holder.tvPayment.setText(product.getPayment());
        holder.tvRating.setText(product.getDeliveryday());
        Glide.with(activity)
                .load(urls.address + "images/"+product.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.cvImage);
        // Store POJO data into string variables
        id = product.getId();
        username = product.getUsername();
        name = product.getName();
        mobileno = product.getMobileno();
        latitude = product.getLatitude();
        longitude = product.getLongitude();
        address = product.getAddress();
        image = product.getImage();
        productname = product.getProductname();
        price = product.getPrice();
        offer = product.getOffer();
        description = product.getDescription();
        deliveryday = product.getDeliveryday();
        shopname = product.getShopname();
        category = product.getCategory();
        productid = product.getProductid();
        payment = product.getPayment();
        holder.btnRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

    }

    private void placeOrder() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.addFinalOrder,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        Log.e("Server Response", response);

                        if (success.equals("1")) {
                            Toast.makeText(activity, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                            RemoveProduct(id);
                        } else {
                            String message = jsonObject.has("message") ? jsonObject.getString("message") : "Failed to place order";
                            Toast.makeText(activity, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "JSON Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(activity, "Volley Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                // Validate and ensure no null values
                params.put("username", username != null ? username : "");
                params.put("name", name != null ? name : "");
                params.put("mobileno", mobileno != null ? mobileno : "");
                params.put("lattitude", latitude != null ? latitude : "");
                params.put("longitude", longitude != null ? longitude : "");
                params.put("address", address != null ? address : "");
                params.put("image", image != null ? image : ""); // Ensure it's a valid URL
                params.put("productname", productname != null ? productname : "");
                params.put("price", price != null ? price : "");
                params.put("offer", offer != null ? offer : "");
                params.put("discription", description != null ? description : ""); // Fixed typo
                params.put("delivery", deliveryday != null ? deliveryday : ""); // Fixed field name
                params.put("shopname", shopname != null ? shopname : "");
                params.put("category", category != null ? category : "");
                params.put("productid", productid != null ? productid : "");
                params.put("payment", payment != null ? payment : "");

                return params;
            }
        };

        // Add request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
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
                        for (int i = 0; i < pojoOrders.size(); i++) {
                            if (pojoOrders.get(i).getId().equals(id)) {
                                pojoOrders.remove(i);
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i,pojoOrders.size());
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
        return pojoOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cvImage;
        TextView tvProductname, tvCategory, tvPrice, tvOffer, tvRating, tvDis, tvName, tvPayment;
        AppCompatButton btnRemoveProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvImage = itemView.findViewById(R.id.cvImage);
            tvProductname = itemView.findViewById(R.id.tvProductname);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOffer = itemView.findViewById(R.id.tvOffer);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvDis = itemView.findViewById(R.id.tvDis);
            tvName = itemView.findViewById(R.id.tvName);
            tvPayment = itemView.findViewById(R.id.tvPayment);
            btnRemoveProduct = itemView.findViewById(R.id.btnRemmoveProduct);
        }
    }
}
