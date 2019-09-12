package com.example.movieevent.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.movieevent.R;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.event.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventNotif {

    private String TAG = getClass().getName();

    Context context;
    String eventId;
    String time;
    int notifId;
    Model model;
    private NotificationManagerCompat notifManager;

    public static final String CHANNEL_ID = "channelId";

    public EventNotif(Context context, String eventId, String time, int notifId) {
        this.context = context;
        notifManager = NotificationManagerCompat.from(context);
        this.eventId = eventId;
        this.time = time;
        this.notifId = notifId;
        model = ModelImpl.getSingletonInstance(context);
    }

    public void sendNotif(){

        Intent broadcastIntentDissmiss = new Intent(context, DissmissReceiver.class);
        broadcastIntentDissmiss.putExtra("notifId", notifId);
        broadcastIntentDissmiss.putExtra("eventId", eventId);

        Intent broadcastIntentRemindMe = new Intent(context, RemindMeReceiver.class);
        broadcastIntentRemindMe.putExtra("eventId", eventId);
        broadcastIntentRemindMe.putExtra("notifId", notifId);

        Intent broadcastIntentCancel = new Intent(context, CancelEventReceiver.class);
        broadcastIntentCancel.putExtra("eventId", eventId);
        broadcastIntentCancel.putExtra("notifId", notifId);

        PendingIntent actionDismiss = PendingIntent.getBroadcast(context, notifId , broadcastIntentDissmiss, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent actionRemindMe = PendingIntent.getBroadcast(context, notifId , broadcastIntentRemindMe, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent actionCancel = PendingIntent.getBroadcast(context, notifId , broadcastIntentCancel, PendingIntent.FLAG_UPDATE_CURRENT);

        if(model.getEventById(eventId) != null ){
            Event event = model.getEventById(eventId);

            String title = event.getTitle();
            String message = "This event starts at "+event.getStartDate()+".";

            Notification notif = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setSmallIcon(R.drawable.movie_icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setChannelId(CHANNEL_ID)
                    .setAutoCancel(true)
                    .addAction(R.drawable.movie_icon, "dismiss", actionDismiss)
                    .addAction(R.drawable.movie_icon, "cancel", actionCancel)
                    .addAction(R.drawable.movie_icon, "remind me", actionRemindMe)
                    .build();
            notifManager.notify(notifId, notif);
        }


    }
}
