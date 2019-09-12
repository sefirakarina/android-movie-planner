package com.example.movieevent.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.view.View;

import com.example.movieevent.model.LoadSharedPref;
import com.example.movieevent.view.CalendarActivity;
import com.example.movieevent.view.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class SaveSettingListener implements View.OnClickListener {

    private static final String PREF_FILE_NAME = "com.example.movieevent.userpreferences";
    private Activity activity;
    Editable notifThreshold, notifPeriod, remindMe;
    LoadSharedPref loadSharedPref;

    public SaveSettingListener(Activity activity, Editable notifThreshold, Editable notifPeriod, Editable remindMe) {
        this.activity = activity;
        this.notifThreshold = notifThreshold;
        this.notifPeriod = notifPeriod;
        this.remindMe = remindMe;
        loadSharedPref = LoadSharedPref.getSingletonInstance(activity);
    }

    @Override
    public void onClick(View v) {

        new Thread(new Runnable() {
            public void run() {

                SharedPreferences.Editor editor = activity.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE).edit();
                editor.putInt("notifThreshold", Integer.parseInt(notifThreshold.toString()));
                editor.putInt("notifPeriod", Integer.parseInt(notifPeriod.toString()));
                editor.putInt("remindMe", Integer.parseInt(remindMe.toString()));
                editor.apply();

            }
        }).start();

        loadSharedPref.setNotifThreshold(Integer.parseInt(notifThreshold.toString()));
        loadSharedPref.setNotifPeriod(Integer.parseInt(notifPeriod.toString()));
        loadSharedPref.setRemindMe(Integer.parseInt(remindMe.toString()));

        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

}
