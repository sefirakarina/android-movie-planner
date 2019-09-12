package com.example.movieevent.services;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.movieevent.R;
import com.example.movieevent.controller.SortEventListener;
import com.example.movieevent.http.HttpURLConnectionAsyncTask;
import com.example.movieevent.model.LoadFiles;
import com.example.movieevent.model.LoadSharedPref;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.event.Event;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class LocationGetter {


    private static Context context;
    private static Activity activity;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private static Context applicationContext;

    private Alarm alarm;
    private FusedLocationProviderClient fusedLocationClient;
    Location currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallback = new LocationCallback();

    Model model;
    ArrayList<Event> events;

    public LocationGetter (Activity activity, Context context) {
        this.context = context;
        this.activity = activity;
    }

    public void setActivityAndContext(Activity activity, Context context){
        this.context = context;
        this.activity = activity;
    }

    public void setAlarm(Alarm alarm){
        this.alarm = alarm;
    }

    private static class LazyHolder {
        static final LocationGetter INSTANCE = new LocationGetter(activity, context);
    }

    public static LocationGetter getSingletonInstance(Context appContext) {

        if (applicationContext == null) {
            applicationContext = appContext;
        }
        return LazyHolder.INSTANCE;
    }


    public void getLocation(){

        createLocationRequest();

        if(checkPermission()){

            model = ModelImpl.getSingletonInstance(context);
            events = model.getAllEvents();

            Collections.sort(events, new SortEventListener.SortDate());

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null /* Looper */);

            fusedLocationClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {

                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.

                    if (location != null) {
                        currentLocation = location;
                        double lat = currentLocation.getLatitude();
                        double lng = currentLocation.getLongitude();

                        for(Event event : events){

                            String eventLocation = event.getLocation();
                            String[] splitLocation = eventLocation.split(",", 2);

                            HttpURLConnectionAsyncTask httpURLConnectionAsyncTask = new HttpURLConnectionAsyncTask();
                            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+ lat + "," + lng + "&destinations=" + Double.parseDouble(splitLocation[0])+","
                                    + Double.parseDouble(splitLocation[1]) + "&key=AIzaSyBv8SDb_qSJAaQKrkMkugurszYqCLEHuvg";

                            httpURLConnectionAsyncTask.setURLAndEvent(url, event, context);

                            httpURLConnectionAsyncTask.setAlarm(alarm);
                            httpURLConnectionAsyncTask.execute();


                        }

                    }
                    alarm.setCheckOrRemind(3);
                    alarm.startAlarm();
                }
            });

        }

    }

    protected void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public boolean checkPermission(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            new AlertDialog.Builder(this.context)
                .setTitle(R.string.title_location_permission)
                .setMessage(R.string.text_location_permission)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION);
                    }
                })
                .create()
                .show();

            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }

            return false;
        } else {

            return true;
        }
    }

}
