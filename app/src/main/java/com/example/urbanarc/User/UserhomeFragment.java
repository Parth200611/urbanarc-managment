package com.example.urbanarc.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.urbanarc.R;
import com.example.urbanarc.User.ADAPTERCLASS.AdpterUserHomefeagmentcategory;
import com.example.urbanarc.User.POJOCLASS.POJOUserFragmentCategory;
import com.example.urbanarc.comman.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UserhomeFragment extends Fragment {
    ImageSlider isOfferImage;
    RecyclerView rvcategory;
    List<POJOUserFragmentCategory> pojoUserFragmentCategories;
    AdpterUserHomefeagmentcategory adpterUserHomefeagmentcategory;
    SearchView searchView;
    TextView tyvnocategpry;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_userhome, container, false);
        isOfferImage = view.findViewById(R.id.isUserHomeFragmentOfferImageslider);
        rvcategory = view.findViewById(R.id.rvUserHomefragmentCategoryproduct);
        pojoUserFragmentCategories=new ArrayList<>();
        searchView = view.findViewById(R.id.svUserhomefargmentrsearchproduct);


        AdpterUserHomefeagmentcategory adpterUserHomefeagmentcategory;


        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();
        slideModelArrayList.add(new SlideModel(R.drawable.offerimage7, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.offerimage5, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.offerimage6, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.offerimage8, ScaleTypes.CENTER_CROP));

        isOfferImage.setImageList(slideModelArrayList);
        isOfferImage.setSlideAnimation(AnimationTypes.ZOOM_IN);

        rvcategory.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.HORIZONTAL,false));





        getAllCtegoryofProduct();




        return view;
    }



    private void getAllCtegoryofProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls.Getallcategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("getallcategory");
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strid=jsonObject1.getString("id");
                        String strcategoryimage=jsonObject1.getString("categoryimage");
                        String strcategoryname=jsonObject1.getString("categoryname");
                        pojoUserFragmentCategories.add(new POJOUserFragmentCategory(strid,strcategoryimage,strcategoryname));
                    }
                    adpterUserHomefeagmentcategory = new AdpterUserHomefeagmentcategory(pojoUserFragmentCategories,getActivity());
                    rvcategory.setAdapter(adpterUserHomefeagmentcategory);




                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }
}