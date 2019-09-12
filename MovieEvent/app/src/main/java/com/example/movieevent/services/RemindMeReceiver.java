package com.example.movieevent.services;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.movieevent.model.LoadSharedPref;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;

public class RemindMeReceiver extends BroadcastReceiver {

    private LoadSharedPref loadSharedPref;
    private Model model;
    private int notifId;

    @Override
    public void onReceive(Context context, Intent intent) {

        model = ModelImpl.getSingletonInstance(context);

        loadSharedPref = LoadSharedPref.getSingletonInstance(context);

        int minute = loadSharedPref.getRemindMe();

        if(model.isNetworkConnected()){
            String eventId = intent.getStringExtra("eventId");
            String time = intent.getStringExtra("time");
            model.addDismissedEventId(eventId);
            Alarm alarm = new Alarm(context, minute, 2);
            alarm.setTimeAndEventId(time, eventId);
            alarm.startAlarm();
        }
        notifId = intent.getIntExtra("notifId", 0);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notifId);
    }
}
