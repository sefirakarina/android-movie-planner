package com.example.movieevent.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.movieevent.model.LoadSharedPref;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;

public class VoidReceiver extends BroadcastReceiver {

    private LoadSharedPref loadSharedPref;
    Model model;

    @Override
    public void onReceive(Context context, Intent intent) {

        model = ModelImpl.getSingletonInstance(context);

        loadSharedPref = LoadSharedPref.getSingletonInstance(context);
        int minute = loadSharedPref.getNotifPeriod();

        if(model.isNetworkConnected()){
            new Alarm(context, minute, 3);
        }

    }
}
