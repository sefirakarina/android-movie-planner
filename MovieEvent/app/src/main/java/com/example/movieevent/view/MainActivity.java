package com.example.movieevent.view;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import 	android.arch.lifecycle.ViewModelProviders;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.movieevent.R;
import com.example.movieevent.adapter.EventAdapter;
import com.example.movieevent.controller.GoToAddEventListener;
import com.example.movieevent.controller.SortEventListener;
import com.example.movieevent.http.HttpURLConnectionAsyncTask;
import com.example.movieevent.model.DBHelper;
import com.example.movieevent.model.LoadFiles;
import com.example.movieevent.model.LoadSharedPref;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.attendee.Attendee;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.model.viewmodel.EventViewModel;
import com.example.movieevent.services.Alarm;
import com.example.movieevent.services.AlertReceiverCheckDistance;
import com.example.movieevent.services.App;
import com.example.movieevent.services.CheckNetworkReceiver;
import com.example.movieevent.services.EventNotif;
import com.example.movieevent.services.LocationGetter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private String TAG = getClass().getName();

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Event> eventsList;

    LocationGetter locationGetter = LocationGetter.getSingletonInstance(this);

    FloatingActionButton btnAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddEvent = findViewById(R.id.btnAddEvent);

        btnAddEvent.setOnClickListener(new GoToAddEventListener(MainActivity.this));

        locationGetter.setActivityAndContext(MainActivity.this, this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        EventViewModel eventViewModel= ViewModelProviders.of(this).get(EventViewModel.class);

        eventViewModel.getEvents(MainActivity.this).observe(this, new Observer<ArrayList<Event>>() {

            @Override
            public void onChanged(ArrayList<Event> events) {

                eventsList= events;

                myAdapter = new EventAdapter(MainActivity.this, events);

                recyclerView = findViewById(R.id.listEvents);
                recyclerView.setHasFixedSize(true);

                layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setAdapter(myAdapter);

                myAdapter.notifyDataSetChanged();

            }


        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();

        new Thread(new Runnable() {
            public void run() {
                DBHelper db= new DBHelper(MainActivity.this);
                db.open();
                db.repopulateDB(eventsList);
                db.close();

            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.layout_calendar:
                Intent intent = new Intent(MainActivity.this,CalendarActivity.class);
                startActivity(intent);
                this.finish();
                return true;

            case R.id.sort:
                item.setOnMenuItemClickListener(new SortEventListener(MainActivity.this, myAdapter, eventsList, recyclerView));
                return true;

            case R.id.setting:
                Intent intent2 = new Intent(MainActivity.this,Setting.class);
                startActivity(intent2);
                return true;

            case R.id.layout_maps:
                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
