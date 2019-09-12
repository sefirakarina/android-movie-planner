package com.example.movieevent.services;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;

public class DissmissReceiver extends BroadcastReceiver {

    Model model;

    @Override
    public void onReceive(Context context, Intent intent) {

        int notificationId = intent.getIntExtra("notifId", 0);
        model = ModelImpl.getSingletonInstance(context);

        String eventId = intent.getStringExtra("eventId");
        model.addDismissedEventId(eventId);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
    }
}
