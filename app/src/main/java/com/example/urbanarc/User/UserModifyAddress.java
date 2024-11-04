package com.example.urbanarc.User;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.example.urbanarc.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.urbanarc.databinding.ActivityUserModifyAddressBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserModifyAddress extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
private ActivityUserModifyAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityUserModifyAddressBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Set a default location
        LatLng defaultLocation = new LatLng(-34, 151);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));

        // Set a click listener on the map
        mMap.setOnMapClickListener(latLng -> {
            mMap.clear(); // Clear previous markers
            mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            // Get latitude and longitude
            double latitude = latLng.latitude;
            double longitude = latLng.longitude;

            // Use Geocoder to get address
            String address = getAddressFromLatLng(latitude, longitude);

            if (address != null) {
                // Pass the data to the next activity
                Intent intent = new Intent(UserModifyAddress.this, UseraddressModify.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("address", address);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Unable to fetch address. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to get address from latitude and longitude
    private String getAddressFromLatLng(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                return address.getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
