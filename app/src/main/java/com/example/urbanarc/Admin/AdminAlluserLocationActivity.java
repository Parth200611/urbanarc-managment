package com.example.urbanarc.Admin;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.example.urbanarc.R;
import com.example.urbanarc.comman.urls;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.urbanarc.databinding.ActivityAdminAlluserLocationBinding;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AdminAlluserLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityAdminAlluserLocationBinding binding;
    
    double lattitude,longitude;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminAlluserLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(AdminAlluserLocationActivity.this,R.color.green));
        
        if (ActivityCompat.checkSelfPermission(AdminAlluserLocationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(AdminAlluserLocationActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) != 
        PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AdminAlluserLocationActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },101);
            
        }else{
            getMylocation();
        }
        
        

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    private void getMylocation() {
        //fusedlocationprovbiderclient use to get user current location
        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(AdminAlluserLocationActivity.this);

        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                new CancellationToken() {
                    @NonNull
                    @Override
                    public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                        return null;
                    }

                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                lattitude=location.getLatitude();
                longitude=location.getLongitude();

                Geocoder geocoder = new Geocoder(AdminAlluserLocationActivity.this);
                try {
                    List<Address> addresses = geocoder.getFromLocation(lattitude,longitude,1);
                    address=addresses.get(0).getAddressLine(0);

                    LatLng currentlocation=new LatLng(lattitude,longitude);
                    mMap.addMarker(new MarkerOptions().position(currentlocation).title(address));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlocation,16),6000,null);
                    Toast.makeText(AdminAlluserLocationActivity.this, address, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminAlluserLocationActivity.this, "Error:"+e.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getAlluserlocation();

    }

    private void getAlluserlocation() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(urls.AdminGetAlluserlocation,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getallcustomerlocation");
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strid=jsonObject.getString("id");
                        String strname =jsonObject.getString("name");
                        double strlattitude = Double.parseDouble(jsonObject.getString("lattitude"));
                        double strlongitude = Double.parseDouble(jsonObject.getString("longuitude"));
                        String straddress = jsonObject.getString("address");
                        String strusername = jsonObject.getString("username");

                        LatLng currentlocationuser = new LatLng(strlattitude,strlongitude);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(currentlocationuser).title(straddress);
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.userlocatuion));
                        mMap.addMarker(markerOptions);

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AdminAlluserLocationActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}