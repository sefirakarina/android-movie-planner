package com.example.movieevent.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import static android.content.Context.MODE_PRIVATE;


public class LoadSharedPref extends AsyncTask<Void, Void, Void> {

    private static final String PREF_FILE_NAME = "com.example.movieevent.userpreferences";
    int notifThreshold, remindMe, notifPeriod;
    private static Context context;

    private static LoadSharedPref singletonInstance;

    public static LoadSharedPref getSingletonInstance(Context context)
    {
        if (singletonInstance == null)
        {
            singletonInstance = new LoadSharedPref(context.getApplicationContext());
        }
        return singletonInstance;
    }

    public int getNotifThreshold() {
        return notifThreshold;
    }

    public void setNotifThreshold(int notifThreshold) {
        this.notifThreshold = notifThreshold;
    }

    public int getRemindMe() {
        return remindMe;
    }

    public void setRemindMe(int remindMe) {
        this.remindMe = remindMe;
    }

    public int getNotifPeriod() {
        return notifPeriod;
    }

    public void setNotifPeriod(int notifPeriod) {
        this.notifPeriod = notifPeriod;
    }

    public LoadSharedPref(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        SharedPreferences pref = context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        notifThreshold = pref.getInt("notifThreshold", 1); // 2nd argument is the default value
        notifPeriod = pref.getInt("notifPeriod", 1);
        remindMe = pref.getInt("remindMe", 1);

        return null;
    }


}
