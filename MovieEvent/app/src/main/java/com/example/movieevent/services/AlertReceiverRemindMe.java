package com.example.movieevent.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Random;

public class AlertReceiverRemindMe extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String eventId = intent.getStringExtra("eventId");
        String time = intent.getStringExtra("time");

        Random ran = new Random();
        int randomInt = 900 + ran.nextInt(10000 - 900 + 1);

        EventNotif eventNotif = new EventNotif(context, eventId, time, randomInt);
        eventNotif.sendNotif();
    }
}
