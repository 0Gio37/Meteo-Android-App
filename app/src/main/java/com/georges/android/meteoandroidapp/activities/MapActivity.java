package com.georges.android.meteoandroidapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.georges.android.meteoandroidapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.georges.android.meteoandroidapp.databinding.ActivityMapBinding;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapBinding binding;
    private double mCurrentLatitude;
    private double mCurrentLongitude;
    private String mCurrentNameCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapBinding.inflate(getLayoutInflater());
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

        Bundle bundle =  getIntent().getExtras();
        mCurrentLatitude = bundle.getDouble("latitude");
        mCurrentLongitude = bundle.getDouble("longitude");
        mCurrentNameCity = bundle.getString("nameCity");


        // Add a marker in my favorite city and move the camera
        LatLng currentCity = new LatLng(mCurrentLatitude, mCurrentLongitude);
        mMap.addMarker(new MarkerOptions().position(currentCity).title(mCurrentNameCity));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentCity));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));


    }
}