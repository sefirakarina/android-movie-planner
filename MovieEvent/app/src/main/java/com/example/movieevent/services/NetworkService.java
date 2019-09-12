package com.example.movieevent.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;


public class NetworkService extends Service {

    private BroadcastReceiver broadcastReceiver;
    boolean isRegis = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        registerScreenOffReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        unregisterReceiver(broadcastReceiver);
        isRegis = false;
        broadcastReceiver = null;
    }


    private void registerScreenOffReceiver() {

        broadcastReceiver = new CheckNetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, filter);
        isRegis = true;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        stopSelf();
        Alarm.cancelAlarmCheckDistance();
        Alarm.cancelAlarmVoid();
        Alarm.cancelAlarmRemindMe();

    }


}
