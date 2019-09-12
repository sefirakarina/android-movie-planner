package com.example.movieevent.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;

public class AlertReceiverCheckDistance extends BroadcastReceiver {

    Model model;

    @Override
    public void onReceive(Context context, Intent intent) {

        model = ModelImpl.getSingletonInstance(context);

        String eventId = intent.getStringExtra("eventId");
        String time = intent.getStringExtra("time");
        int notifId = intent.getIntExtra("notifId", 0);

        if(!model.getDismissedEventId().isEmpty()){
            for(String dismissedEvent : model.getDismissedEventId()){

                if(dismissedEvent == eventId){
                    EventNotif eventNotif = new EventNotif(context, eventId, time, notifId);
                    eventNotif.sendNotif();
                }
            }
        }
        else {
            EventNotif eventNotif = new EventNotif(context, eventId, time, notifId);
            eventNotif.sendNotif();
        }
    }
}
