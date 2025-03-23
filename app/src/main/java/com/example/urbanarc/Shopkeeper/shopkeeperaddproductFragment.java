package com.example.urbanarc.Shopkeeper;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.urbanarc.R;
import com.example.urbanarc.User.userMyprofilActivity;
import com.example.urbanarc.comman.VolleyMultipartRequest;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class shopkeeperaddproductFragment extends Fragment {
    private Spinner spinnerCategory;
    private EditText etShopName, etProductName, etProductPrice, etProductDescription, etProductOffer, etProductRating,etDelivery;
    private ImageView ivImage;
    private Button btnAddImage, btnAddProduct;
    private String shopName, productName, productPrice, productDescription, productOffer, productRating, selectedCategory,delivery;
    private  int pick_image_request=789;
    Bitmap bitmap;
    Uri filepath;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_shopkeeperaddproduct, container, false);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        etShopName = view.findViewById(R.id.etshopname);
        etProductName = view.findViewById(R.id.etProductname);
        etProductPrice = view.findViewById(R.id.etProductPrice);
        etProductDescription = view.findViewById(R.id.etProductdiscription);
        etProductOffer = view.findViewById(R.id.etProductOffer);
        etProductRating = view.findViewById(R.id.etProductRating);
        ivImage = view.findViewById(R.id.ivImage);
        btnAddImage = view.findViewById(R.id.btnAddImage);
        btnAddProduct = view.findViewById(R.id.btnAddProduct);
        etDelivery = view.findViewById(R.id.etDelivery);
        List<String> categories = new ArrayList<>();
        categories.add("Chair");
        categories.add("Bed");
        categories.add("Chandeliers");
        categories.add("Cupboard");
        categories.add("Dinning");
        categories.add("Interior Decor");
        categories.add("Lamp");
        categories.add("Plants");
        categories.add("Sofa");
        categories.add("Table");

        // Adapter for Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapter);

        // Set listener for category selection
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categories.get(position);
                Toast.makeText(getContext(), "Selected: " + selectedCategory, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // Set up event listeners
        btnAddImage.setOnClickListener(v -> {
            SelectUserProfileimage();
        });

        btnAddProduct.setOnClickListener(v -> {
            validateAndPostData();
        });
        return view;
    }
    private void validateAndPostData() {
        // Assign input values to global string variables
        shopName = etShopName.getText().toString().trim();
        productName = etProductName.getText().toString().trim();
        productPrice = etProductPrice.getText().toString().trim();
        productDescription = etProductDescription.getText().toString().trim();
        productOffer = etProductOffer.getText().toString().trim();
        productRating = etProductRating.getText().toString().trim();
        delivery = etDelivery.getText().toString().trim();

        // Check if any field is empty
        if (TextUtils.isEmpty(shopName) || TextUtils.isEmpty(productName) ||
                TextUtils.isEmpty(productPrice) || TextUtils.isEmpty(productDescription) ||
                TextUtils.isEmpty(productOffer) || TextUtils.isEmpty(productRating) ||
                TextUtils.isEmpty(selectedCategory)) {
            Toast.makeText(getContext(), "Please enter all details", Toast.LENGTH_SHORT).show();
        } else {
            // All fields are filled, proceed to post data
           PostDATA();
        }
    }

    private void PostDATA() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("category",selectedCategory);
        params.put("productname",productName);
        params.put("shopname",shopName);
        params.put("price",productPrice);
        params.put("discription",productDescription);
        params.put("offer",productOffer);
        params.put("rating",productRating);
        params.put("deliveryday",delivery);
        client.post(urls.INSERTpRODUCT,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(getActivity(), "Product Added", Toast.LENGTH_SHORT).show();
                        UserImageSaveTodatabase(bitmap,shopName);
                        clearFields();

                    }else {
                        Toast.makeText(getActivity(), "Fail To add Product", Toast.LENGTH_SHORT).show();
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

    private void clearFields() {
        etShopName.setText("");
        etProductName.setText("");
        etProductPrice.setText("");
        etProductDescription.setText("");
        etProductOffer.setText("");
        etProductRating.setText("");
        etDelivery.setText("");
        ivImage.setImageResource(R.drawable.baseline_camera_alt_24); // Set camera icon after clearing
        spinnerCategory.setSelection(0); // Reset spinner to first category
    }

    private void SelectUserProfileimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image For Profil"),pick_image_request);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==pick_image_request && resultCode==RESULT_OK && data!=null){
            filepath=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filepath);
                ivImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void UserImageSaveTodatabase(Bitmap bitmap, String struername) {

        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.AddProductimage, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(getActivity(), "Image Save as Profil ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(getActivity(), "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("tags", struername); // Adjusted to match PHP parameter name
                return parms;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,VolleyMultipartRequest.DataPart> parms = new HashMap<>();
                long imagename = System.currentTimeMillis();
                parms.put("pic",new VolleyMultipartRequest.DataPart(imagename+".jpeg",getfiledatafromBitmap(bitmap)));

                return parms;

            }

        };
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}