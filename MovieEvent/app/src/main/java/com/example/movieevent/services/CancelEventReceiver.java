package com.example.movieevent.services;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.movieevent.view.CancelDialog;

public class CancelEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int notificationId = intent.getIntExtra("notifId", 0);
        Intent cancelDialogIntent = new Intent(context, CancelDialog.class);

        String eventId = intent.getStringExtra("eventId");

        cancelDialogIntent.putExtra("eventId", eventId);
        cancelDialogIntent.putExtra("notificationId", notificationId);

        context.startActivity(cancelDialogIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
    }
}