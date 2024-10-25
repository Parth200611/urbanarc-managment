package com.example.urbanarc.User;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.urbanarc.SpacingItemDecoration;
import com.example.urbanarc.User.ADAPTERCLASS.AdapterGetUserFragmentHomepageBed;
import com.example.urbanarc.User.ADAPTERCLASS.AdapterUserHomeFragmentCategoryWiseproduct;
import com.example.urbanarc.User.ADAPTERCLASS.AdapterUserHomeFragmentPlantData;
import com.example.urbanarc.User.ADAPTERCLASS.AdpterUserHomefeagmentcategory;
import com.example.urbanarc.User.POJOCLASS.POJOUsehomeFragmentcategory2;
import com.example.urbanarc.User.POJOCLASS.POJOUserFragmentCategory;
import com.example.urbanarc.User.POJOCLASS.POJOUserFragmentHomepagePlantData;
import com.example.urbanarc.User.POJOCLASS.POJOgetCtaegoryWiseproductHomepage;
import com.example.urbanarc.comman.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UserhomeFragment extends Fragment {
    ImageSlider isOfferImage;
    RecyclerView rvcategory,rvcategorywiseproductlist1,rvcategorywiseproductlist2,rvcategorywiseproductlist3;
    List<POJOUserFragmentCategory> pojoUserFragmentCategories;
    AdpterUserHomefeagmentcategory adpterUserHomefeagmentcategory;
    SearchView searchView;
    TextView tyvnocategpry1,tvcategory1,tvcategory2,tvcategory3;
    List<POJOgetCtaegoryWiseproductHomepage> pojOgetCtaegoryWiseproductHomepages;
    List<POJOUsehomeFragmentcategory2> pojoUsehomeFragmentcategory2s;
    List<POJOUserFragmentHomepagePlantData> pojoUserFragmentHomepagePlantData;
    AdapterUserHomeFragmentCategoryWiseproduct adapterUserHomeFragmentCategoryWiseproduct;
    AdapterGetUserFragmentHomepageBed adapterGetUserFragmentHomepageBed;
    AdapterUserHomeFragmentPlantData adapterUserHomeFragmentPlantData;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_userhome, container, false);
        isOfferImage = view.findViewById(R.id.isUserHomeFragmentOfferImageslider);
        rvcategory = view.findViewById(R.id.rvUserHomefragmentCategoryproduct);
        pojoUserFragmentCategories=new ArrayList<>();
        searchView = view.findViewById(R.id.svUserhomefargmentrsearchproduct);
        tvcategory1 = view.findViewById(R.id.tvUserHomeFragmentCategory1);
        tvcategory2 = view.findViewById(R.id.tvUserHomeFragmentCategory2);
        tvcategory3 = view.findViewById(R.id.tvUserHomeFragmentCategory3);
        rvcategorywiseproductlist1 = view.findViewById(R.id.rvUserHomefragmentCategorywiseproductlist);
        rvcategorywiseproductlist2 = view.findViewById(R.id.rvUserHomefragmentCategorywiseproductlist2);
        rvcategorywiseproductlist3 = view.findViewById(R.id.rvUserHomefragmentCategorywiseproductlist3);
        pojoUsehomeFragmentcategory2s = new ArrayList<>();
        pojOgetCtaegoryWiseproductHomepages = new ArrayList<>();
        pojoUserFragmentHomepagePlantData = new ArrayList<>();




        AdpterUserHomefeagmentcategory adpterUserHomefeagmentcategory;


        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();
        slideModelArrayList.add(new SlideModel(R.drawable.offerimage7, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.offerimage5, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.offerimage6, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.offerimage8, ScaleTypes.CENTER_CROP));

        isOfferImage.setImageList(slideModelArrayList);
        isOfferImage.setSlideAnimation(AnimationTypes.ZOOM_IN);

        rvcategory.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.HORIZONTAL,false));
        rvcategorywiseproductlist1.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.HORIZONTAL,false));
        rvcategorywiseproductlist2.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.HORIZONTAL,false));
        rvcategorywiseproductlist3.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.HORIZONTAL,false));



        getAllCtegoryofProduct();
        getCategorywiseproduct1();
        getCategorywiseproduct2();
        getCategorywiseproduct3();




        return view;
    }

    private void getCategorywiseproduct3() {
        RequestQueue requestQueue3 = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, urls.UserHomepagecategory3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject5 = new JSONObject(response);
                    JSONArray jsonArray4 = jsonObject5.getJSONArray("getusercategoryhomePlant");
                    for (int j=0;j<jsonArray4.length();j++){
                        JSONObject jsonObject6 = jsonArray4.getJSONObject(j);
                        String strid = jsonObject6.getString("id");
                        String strimage = jsonObject6.getString("image");
                        String strcategory = jsonObject6.getString("category");
                        String strproductname = jsonObject6.getString("productname");
                        String strprice = jsonObject6.getString("price");
                        String stroffer = jsonObject6.getString("offer");
                        String strdiscription = jsonObject6.getString("discription");
                        String strrating = jsonObject6.getString("rating");
                        tvcategory3.setText(strcategory);
                        pojoUserFragmentHomepagePlantData.add(new POJOUserFragmentHomepagePlantData(strid,strimage,strcategory,strproductname,strprice,stroffer,strdiscription,strrating));
                    }
                    adapterUserHomeFragmentPlantData = new AdapterUserHomeFragmentPlantData(pojoUserFragmentHomepagePlantData,getActivity());
                    rvcategorywiseproductlist3.setAdapter(adapterUserHomeFragmentPlantData);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue3.add(stringRequest3);

    }

    private void getCategorywiseproduct2() {
        RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, urls.UserHomepagecategory2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject4 = new JSONObject(response);
                    JSONArray jsonArray3 = jsonObject4.getJSONArray("getusercategoryhomebed");
                    for (int j=0;j<jsonArray3.length();j++){
                        JSONObject jsonObject5 = jsonArray3.getJSONObject(j);
                        String strid = jsonObject5.getString("id");
                        String strimage = jsonObject5.getString("image");
                        String strcategory = jsonObject5.getString("category");
                        String strproductname = jsonObject5.getString("productname");
                        String strprice = jsonObject5.getString("price");
                        String stroffer = jsonObject5.getString("offer");
                        String strdiscription = jsonObject5.getString("discription");
                        String strrating = jsonObject5.getString("rating");
                        tvcategory2.setText(strcategory);
                        pojoUsehomeFragmentcategory2s.add(new POJOUsehomeFragmentcategory2(strid,strimage,strcategory,strproductname,strprice,stroffer,strdiscription,strrating));
                    }
                    adapterGetUserFragmentHomepageBed = new AdapterGetUserFragmentHomepageBed(pojoUsehomeFragmentcategory2s,getActivity());
                    rvcategorywiseproductlist2.setAdapter(adapterGetUserFragmentHomepageBed);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue2.add(stringRequest2);

    }

    private void getCategorywiseproduct1() {
        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, urls.UserHomepagecategory1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject3 = new JSONObject(response);
                    JSONArray jsonArray2 = jsonObject3.getJSONArray("getusercategoryhomesofa");
                    for (int j=0;j<jsonArray2.length();j++){
                        JSONObject jsonObject4 = jsonArray2.getJSONObject(j);
                        String strid = jsonObject4.getString("id");
                        String strimage = jsonObject4.getString("image");
                        String strcategory = jsonObject4.getString("category");
                        String strproductname = jsonObject4.getString("productname");
                        String strprice = jsonObject4.getString("price");
                        String stroffer = jsonObject4.getString("offer");
                        String strdiscription = jsonObject4.getString("discription");
                        String strrating = jsonObject4.getString("rating");
                        tvcategory1.setText(strcategory);
                        pojOgetCtaegoryWiseproductHomepages.add(new POJOgetCtaegoryWiseproductHomepage(strid,strimage,strcategory,strproductname,strprice,stroffer,strdiscription,strrating));
                    }
                    adapterUserHomeFragmentCategoryWiseproduct = new AdapterUserHomeFragmentCategoryWiseproduct(pojOgetCtaegoryWiseproductHomepages,getActivity());
                    rvcategorywiseproductlist1.setAdapter(adapterUserHomeFragmentCategoryWiseproduct);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue1.add(stringRequest1);

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