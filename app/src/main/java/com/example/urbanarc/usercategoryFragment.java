package com.example.urbanarc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class usercategoryFragment extends Fragment {

    ListView lvAllcategory;
    TextView tvNocategorypresent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_usercategory, container, false);

        lvAllcategory = view.findViewById(R.id.lvUserCategoaryFragmentcategoarylist);
        tvNocategorypresent = view.findViewById(R.id.tvUsercategoryFragmentTextnocategory);

        getAllcategory();

        return view;
    }

    private void getAllcategory() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post("http://192.168.1.4:80//urbanarcAPI/getallproductcategories.php",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "No category present", Toast.LENGTH_SHORT).show();
            }
        });
    }
}