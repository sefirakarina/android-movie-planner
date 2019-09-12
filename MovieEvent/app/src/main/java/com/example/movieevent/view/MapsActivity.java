package com.example.movieevent.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.movieevent.R;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.event.Event;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<Event> events = new ArrayList<>();
    Model model;

    Date today = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        model = ModelImpl.getSingletonInstance(MapsActivity.this);
        for(Event event : model.getAllEvents()){

            if(event.getStartDate().after(today)){
                events.add(event);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String location;
        String[] splitLocation;

        if(events.size() < 3){
            LatLng def = new LatLng(-37.811363, 144.936962);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(def));
            return;
        }else{
            for(int i = 0; i < 3; i++){
                location = events.get(i).getLocation();
                Log.i(this.getClass().toString(), events.get(i).getId());
                splitLocation = location.split(",", 2);
                LatLng venue = new LatLng(Double.parseDouble(splitLocation[0]), Double.parseDouble(splitLocation[1]));
                mMap.addMarker(new MarkerOptions().position(venue).title(events.get(i).getTitle()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(venue,12));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
