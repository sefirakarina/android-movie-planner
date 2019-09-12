package com.example.movieevent.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.movieevent.model.LoadSharedPref;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;

public class CheckNetworkReceiver extends BroadcastReceiver {

    Model model;
    int minute;
    private LoadSharedPref loadSharedPref;

    public CheckNetworkReceiver() { }

    @Override
    public void onReceive(Context context, Intent intent) {

        model = ModelImpl.getSingletonInstance(context);

        loadSharedPref = LoadSharedPref.getSingletonInstance(context);
        minute = loadSharedPref.getNotifPeriod();

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        if (netInfo != null && netInfo.isConnectedOrConnecting()){

            new Alarm(context, 0, 1);

            model.setNetworkConnected(true);
        } else {

            model.setNetworkConnected(false);
        }
    }
}
