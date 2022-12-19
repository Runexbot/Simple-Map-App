package com.example.gslcmobile;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gslcmobile.database.DBPinpoint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.gslcmobile.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Marker onlineMarker;
    private LatLng currMarker;
    DBPinpoint dbPinpoint;

    Button store, show, hide;

    private List<Location> locationList = new ArrayList<>();

    SearchView sView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        store = findViewById(R.id.storeMarker);
        show = findViewById(R.id.showAll);
        hide = findViewById(R.id.hideAll);

        hide.setVisibility(View.GONE);
        sView = findViewById(R.id.sView);

        dbPinpoint = new DBPinpoint(this);

        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String position = sView.getQuery().toString();

                List<Address> locationList = null;

                if (position.equals("") || position!= null){
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        locationList = geocoder.getFromLocationName(position, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (locationList.isEmpty()){
                        Toast.makeText(MapsActivity.this, "Address doesn't exists!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Address location = locationList.get(0);

                        currMarker = new LatLng(location.getLatitude(), location.getLongitude());
                        if(onlineMarker!= null)
                            onlineMarker.remove();
                        onlineMarker = mMap.addMarker(new MarkerOptions().position(currMarker).title(position));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currMarker, 15));

                    }

                   }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng binus = new LatLng(-6.201695768909265, 106.78225252379241);
        onlineMarker = mMap.addMarker(new MarkerOptions().position(binus).title("Marker in Binus University"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(binus,15));
        currMarker = binus;

        store.setOnClickListener(v -> {
            dbPinpoint.insertPinpoint(currMarker);
            Toast.makeText(this, "Pinpoint Stored!", Toast.LENGTH_SHORT).show();
            store.setVisibility(View.GONE);
        });

        show.setOnClickListener(v -> {
            locationList = dbPinpoint.showAllPinpoint();
            for (int i = 0; i < locationList.size(); i++){
                Location location = locationList.get(i);
                LatLng dot = location.getPinPoint();
                mMap.addMarker(new MarkerOptions().position(dot).title(dot.latitude + " , " + dot.longitude).snippet(location.getId() + ""));
            }
            Toast.makeText(this, "All marker successfully shown on map", Toast.LENGTH_SHORT).show();
            show.setVisibility(View.GONE);
            hide.setVisibility(View.VISIBLE);
        });

        hide.setOnClickListener(v -> {
            mMap.clear();
            onlineMarker = mMap.addMarker(new MarkerOptions().position(currMarker).title(currMarker.latitude + " , " + currMarker.longitude));
            Toast.makeText(this, "All marker are hidden from the map", Toast.LENGTH_SHORT).show();
            show.setVisibility(View.VISIBLE);
            hide.setVisibility(View.GONE);
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
//                allPoints.add(point);
                onlineMarker.remove();
                mMap.clear();
                onlineMarker = mMap.addMarker(new MarkerOptions().position(point).title(point.latitude + " , " + point.longitude));
                currMarker = point;
                store.setVisibility(View.VISIBLE);
            }
        });

    }

}