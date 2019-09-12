package com.example.movieevent.services;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

import com.example.movieevent.model.LoadFiles;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class App extends Application implements PropertyChangeListener {

    public static final String CHANNEL_ID = "channelId";
    Intent intent;
    private static Intent networkIntent;

    public App() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotifChanel();
        LoadFiles loadFiles = new LoadFiles(this);
        loadFiles.execute();
        loadFiles.addPropertyChangeListener(this);
    }

    private void createNotifChanel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "channel name",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("put the message here!!");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals("finishLoadingFile")) {
            networkIntent = new Intent(getBaseContext(), NetworkService.class);
            startService(networkIntent);
        }
    }
}
