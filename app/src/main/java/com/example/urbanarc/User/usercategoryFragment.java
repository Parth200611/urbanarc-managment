package com.example.urbanarc.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanarc.R;
import com.example.urbanarc.User.AdaptergetallCategory;
import com.example.urbanarc.User.POJOgetallcategory;
import com.example.urbanarc.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class usercategoryFragment extends Fragment {

    ListView lvAllcategory;
    TextView tvNocategorypresent;
    List<POJOgetallcategory> pojOgetallcategories;
    AdaptergetallCategory adaptergetallCategory;

    SearchView svSearchCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_usercategory, container, false);

        lvAllcategory = view.findViewById(R.id.lvUserCategoaryFragmentcategoarylist);
        tvNocategorypresent = view.findViewById(R.id.tvUsercategoryFragmentTextnocategory);
        pojOgetallcategories = new ArrayList<>();
        svSearchCategory = view.findViewById(R.id.svCategoryFragmentSearchcategory);

        svSearchCategory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchcategory(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchcategory(query);
                return false;
            }
        });

        getAllcategory();

        return view;
    }

    private void searchcategory(String query) {
        List<POJOgetallcategory> temperoeycategory = new ArrayList<>();
        temperoeycategory.clear();

        for (POJOgetallcategory obj :pojOgetallcategories) {

            if (obj.getCategoryName().toUpperCase().contains(query.toUpperCase())){

                temperoeycategory.add(obj);

            }else {

                tvNocategorypresent.setVisibility(View.VISIBLE);
            }
            adaptergetallCategory = new AdaptergetallCategory(temperoeycategory,getActivity());
            lvAllcategory.setAdapter(adaptergetallCategory);

        }

    }


    private void getAllcategory() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(urls.Getallcategory,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonArray = response.getJSONArray("getallcategory");
                    if (jsonArray.isNull(0)){
                        tvNocategorypresent.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strid = jsonObject.getString("id");
                        String strcategoryimage = jsonObject.getString("categoryimage");
                        String strcategoryname = jsonObject.getString("categoryname");

                        pojOgetallcategories.add(new POJOgetallcategory(strid,strcategoryimage,strcategoryname));
                    }
                    adaptergetallCategory = new AdaptergetallCategory(pojOgetallcategories,getActivity());

                    lvAllcategory.setAdapter(adaptergetallCategory);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}