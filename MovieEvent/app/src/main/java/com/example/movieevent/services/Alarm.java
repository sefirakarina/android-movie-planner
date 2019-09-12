package com.example.movieevent.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;

public class Alarm {

    private String TAG = getClass().getName();

    private static Context context;
    private LocationGetter locationGetter;
    private String time;
    private String eventId;
    private int notifId;
    private Map<String, Integer> notifications;
    private Model model;

    int minute;
    int checkOrRemind;
    Intent intent;

    public Alarm(Context context, int minute, int checkOrRemind) {
        this.context = context;
        this.minute = minute;
        this.checkOrRemind = checkOrRemind;

        if(ModelImpl.getSingletonInstance(context).getAllEvents().size()!=0 && checkOrRemind!=2){
            locationGetter = LocationGetter.getSingletonInstance(context);
            locationGetter.setAlarm(this);
            locationGetter.getLocation();
        }


    }

    public void startAlarm(){

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        model = ModelImpl.getSingletonInstance(context);

        notifications = model.getNotifId();
        if(!notifications.containsKey(eventId) ){
            Random ran = new Random();
            int randomInt = 100 + ran.nextInt(200 - 100 + 1);
            notifId = randomInt;

            notifications.put(eventId, notifId);
        }
        else
            notifId = notifications.get(eventId);

        if(checkOrRemind == 1){
            intent = new Intent(context, AlertReceiverCheckDistance.class);

            intent.putExtra("eventId", eventId);
            intent.setType("text/plain");
            intent.putExtra("time", time);
            intent.setType("text/plain");
            intent.putExtra("notifId", notifId);
        }

        if(checkOrRemind == 2){
            intent = new Intent(context, AlertReceiverRemindMe.class);
            intent.putExtra("eventId", eventId);
            intent.setType("text/plain");
            intent.putExtra("time", time);
            intent.setType("text/plain");
            intent.putExtra("notifId", notifId);
        }

        if(checkOrRemind == 3){
            intent = new Intent(context, VoidReceiver.class);
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, minute);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }

    public void setCheckOrRemind(int i){
        checkOrRemind = i;
    }

    public static void cancelAlarmVoid() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, VoidReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public static void cancelAlarmCheckDistance() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiverCheckDistance.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public static void cancelAlarmRemindMe() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiverRemindMe.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void setTimeAndEventId(String time, String eventId){
        this.time = time;
        this.eventId = eventId;
    }


}
