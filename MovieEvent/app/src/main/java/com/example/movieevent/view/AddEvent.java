package com.example.movieevent.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.movieevent.R;
import com.example.movieevent.controller.AddEventListener;
import com.example.movieevent.controller.DatePickerListener;
import com.example.movieevent.controller.GoToAttendeesListener;
import com.example.movieevent.controller.GoToMoviesListener;
import com.example.movieevent.controller.TimePickerListener;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;

import java.util.UUID;

public class AddEvent extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    Button btnAddEvent, btnAddAttendees, btnChooseMovie;
    EditText etAddEventTitle, etAddEventVenue, etAddEventLocation, etAddStartDate, etAddEndDate, etAddStartTime, etAddEndTime;
    FragmentManager fragmentManager;
    StringBuilder startTime = new StringBuilder();
    StringBuilder endTime = new StringBuilder();
    StringBuilder startDate = new StringBuilder();
    StringBuilder endDate = new StringBuilder();
    Model model;
    String am_pm;
    String addStartTimeTag, addEndTimeTag, addStartDateTag, addEndDateTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        model = ModelImpl.getSingletonInstance(AddEvent.this);

        String id = UUID.randomUUID().toString();

        btnAddEvent = findViewById(R.id.btnAddEvent);
        btnAddAttendees = findViewById(R.id.btnAddAttendees);
        btnChooseMovie = findViewById(R.id.btnChooseMovie);

        etAddEventLocation = findViewById(R.id.etAddEventLocation);
        etAddEventTitle = findViewById(R.id.etAddEventTitle);
        etAddEventVenue = findViewById(R.id.etAddEventVenue);

        etAddStartTime = findViewById(R.id.etStartTime);
        etAddStartDate = findViewById(R.id.etStartDate);
        etAddEndTime = findViewById(R.id.etEndTime);
        etAddEndDate = findViewById(R.id.etEndDate);

        addStartTimeTag = getString(R.string.add_start_time_tag);
        addEndTimeTag = getString(R.string.add_end_time_tag);
        addStartDateTag = getString(R.string.add_start_date_tag);
        addEndDateTag = getString(R.string.add_end_date_tag);

        etAddStartTime.setOnClickListener(new TimePickerListener(getSupportFragmentManager(), addStartTimeTag));
        etAddEndTime.setOnClickListener(new TimePickerListener(getSupportFragmentManager(), addEndTimeTag));
        etAddStartDate.setOnClickListener(new DatePickerListener(getSupportFragmentManager(), addStartDateTag));
        etAddEndDate.setOnClickListener(new DatePickerListener(getSupportFragmentManager(), addEndDateTag));

        btnAddEvent.setOnClickListener(new AddEventListener(AddEvent.this, etAddEventTitle.getText(),
                                        etAddEventLocation.getText(), etAddEventVenue.getText(), id));
        btnChooseMovie.setOnClickListener(new GoToMoviesListener(this, id));
        btnAddAttendees.setOnClickListener(new GoToAttendeesListener(this, id));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        //set the time pickers when user choose time
        fragmentManager = getSupportFragmentManager();

        am_pm = (hourOfDay < 12) ? "AM" : "PM";

        if(fragmentManager.findFragmentByTag(addStartTimeTag) != null){

            startTime.setLength(0);

            if(minute < 10){
                startTime.append(hourOfDay + ":" +  "0" + minute + " " + am_pm);
            }
            else
                startTime.append(hourOfDay + ":" + minute + " " + am_pm);

            etAddStartTime.setText(startTime.toString());

            model.setTempStartTime(etAddStartTime.getText());
        }
        if(fragmentManager.findFragmentByTag(addEndTimeTag) != null){

            endTime.setLength(0);

            if(minute < 10){
                endTime.append(hourOfDay + ":" +  "0" + minute + " " + am_pm);
            }
            else
                endTime.append(hourOfDay + ":" + minute + " " + am_pm);

            etAddEndTime.setText(endTime.toString());

            model.setTempEndTime(etAddEndTime.getText());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        //set the date pickers when user choose date
        fragmentManager = getSupportFragmentManager();

        if(fragmentManager.findFragmentByTag(addStartDateTag) != null){

            startDate.setLength(0);

            startDate.append(dayOfMonth + "/" + (month + 1) + "/" + year);

            etAddStartDate.setText(startDate.toString());

            model.setTempStartDate(etAddStartDate.getText());
        }

        if(fragmentManager.findFragmentByTag(addEndDateTag) != null){

            endDate.setLength(0);

            endDate.append(dayOfMonth + "/" + (month + 1) + "/" + year);

            etAddEndDate.setText(endDate.toString());

            model.setTempEndDate(etAddEndDate.getText());
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
                Intent intent = new Intent(AddEvent.this,MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.layout_calendar:
                Intent intent2 = new Intent(AddEvent.this,CalendarActivity.class);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
