package com.example.movieevent.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.movieevent.R;
import com.example.movieevent.controller.DatePickerListener;
import com.example.movieevent.controller.GoToAttendeesListener;
import com.example.movieevent.controller.GoToMoviesListener;
import com.example.movieevent.controller.ModifyEventListener;
import com.example.movieevent.controller.TimePickerListener;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.services.CheckNetworkReceiver;

import java.text.SimpleDateFormat;

public class ModifyEvent extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private String TAG = getClass().getName();
    Button btnSave;
    Button btnSetMovie;
    Button btnAddAttendees;

    Model model;

    EditText etEditEventTitle, etEditEventVenue, etEditEventLocation, etStartDate, etEndDate, etStartTime, etEndTime;
    StringBuilder startTime = new StringBuilder();
    StringBuilder endTime = new StringBuilder();
    StringBuilder startDate = new StringBuilder();
    StringBuilder endDate = new StringBuilder();
    String am_pm;
    FragmentManager fragmentManager;

    String startTimeTag, endTimeTag, startDateTag, endDateTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_event);

        btnSave = findViewById(R.id.btnSave);
        btnSetMovie = findViewById(R.id.btnChooseMovie);
        btnAddAttendees = findViewById(R.id.btnAddAttendees);

        Intent intent = getIntent();

        String eventId = intent.getStringExtra("eventId");
        model = ModelImpl.getSingletonInstance(ModifyEvent.this);
        Event event = model.getEventById(eventId);

        etEditEventLocation = findViewById(R.id.etEditEventLocation);
        etEditEventTitle = findViewById(R.id.etEditEventTitle);
        etEditEventVenue = findViewById(R.id.etEditEventVenue);

        etStartTime = findViewById(R.id.etStartTime);
        etStartDate = findViewById(R.id.etStartDate);
        etEndTime = findViewById(R.id.etEndTime);
        etEndDate = findViewById(R.id.etEndDate);

        startTimeTag = getString(R.string.start_time_tag);
        endTimeTag = getString(R.string.end_time_tag);
        startDateTag = getString(R.string.start_date_tag);
        endDateTag = getString(R.string.end_date_tag);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        etEditEventVenue.setText(event.getVenue());
        etEditEventTitle.setText(event.getTitle());
        etEditEventLocation.setText(event.getLocation());

        startTime.append(timeFormat.format(event.getStartDate()));
        etStartTime.setText(timeFormat.format(event.getStartDate()));
        etStartDate.setText(dateFormat.format(event.getStartDate()));
        etEndTime.setText(timeFormat.format(event.getEndDate()));
        etEndDate.setText(dateFormat.format(event.getEndDate()));

        model.setTempStartTime(etStartTime.getText());
        model.setTempEndTime(etEndTime.getText());
        model.setTempStartDate(etStartDate.getText());
        model.setTempEndDate(etEndDate.getText());


        btnSave.setOnClickListener(new ModifyEventListener(ModifyEvent.this, eventId, etEditEventTitle.getText(),
                etEditEventLocation.getText(), etEditEventVenue.getText()));
        btnSetMovie.setOnClickListener(new GoToMoviesListener(this, eventId));
        btnAddAttendees.setOnClickListener(new GoToAttendeesListener(this, eventId));

        etStartTime.setOnClickListener(new TimePickerListener(getSupportFragmentManager(), startTimeTag));
        etEndTime.setOnClickListener(new TimePickerListener(getSupportFragmentManager(), endTimeTag));
        etStartDate.setOnClickListener(new DatePickerListener(getSupportFragmentManager(), startDateTag));
        etEndDate.setOnClickListener(new DatePickerListener(getSupportFragmentManager(), endDateTag));
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        // Show the time that user pick in the editText
        fragmentManager = getSupportFragmentManager();

        am_pm = (hourOfDay < 12) ? "AM" : "PM";

        if(fragmentManager.findFragmentByTag(startTimeTag) != null){

            startTime.setLength(0);

            if(minute < 10){
                startTime.append(hourOfDay + ":" +  "0" + minute + " " + am_pm);
            }
            else
                startTime.append(hourOfDay + ":" + minute + " " + am_pm);

            etStartTime.setText(startTime.toString());

            model.setTempStartTime(etStartTime.getText());
        }
        if(fragmentManager.findFragmentByTag(endTimeTag) != null){

            endTime.setLength(0);

            if(minute < 10){
                endTime.append(hourOfDay + ":" +  "0" + minute + " " + am_pm);
            }
            else
                endTime.append(hourOfDay + ":" + minute + " " + am_pm);

            etEndTime.setText(endTime.toString());

            model.setTempEndTime(etEndTime.getText());
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        // Show the date that user pick in the editText
        fragmentManager = getSupportFragmentManager();

        if(fragmentManager.findFragmentByTag(startDateTag) != null){

            startDate.setLength(0);

            startDate.append(dayOfMonth + "/" + (month + 1) + "/" + year);

            etStartDate.setText(startDate.toString());

            model.setTempStartDate(etStartDate.getText());
        }

        if(fragmentManager.findFragmentByTag(endDateTag) != null){

            endDate.setLength(0);

            endDate.append(dayOfMonth + "/" + (month + 1) + "/" + year);

            etEndDate.setText(endDate.toString());

            model.setTempEndDate(etEndDate.getText());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_choice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.layout_list:
                Intent intent = new Intent(ModifyEvent.this,MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.layout_calendar:
                Intent intent2 = new Intent(ModifyEvent.this,CalendarActivity.class);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        model.clearAttendeeList();
        model.resetTempMovie();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
