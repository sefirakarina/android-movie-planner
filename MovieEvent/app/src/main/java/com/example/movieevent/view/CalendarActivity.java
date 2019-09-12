package com.example.movieevent.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.movieevent.R;
import com.example.movieevent.adapter.EventAdapter;
import com.example.movieevent.controller.CalendarClickListener;
import com.example.movieevent.model.DBHelper;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.model.viewmodel.EventViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    Model model = ModelImpl.getSingletonInstance(this);
    private CalendarCustomView customCal;
    private GridView calendarGrid;
    ArrayList<Event> eventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        customCal =  ((CalendarCustomView)findViewById(R.id.custom_calendar));
        customCal.refreshCalendar();

        calendarGrid = findViewById((R.id.calendarGrid));

        Date today = new Date();
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(today);

        model.setEventOnDate(calendarToday.get(Calendar.DAY_OF_MONTH),
                calendarToday.get(Calendar.MONTH)+1, calendarToday.get(Calendar.YEAR));

        EventViewModel eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        eventViewModel.getEvents(CalendarActivity.this).observe(this, new Observer<ArrayList<Event>>() {
            @Override
            public void onChanged(ArrayList<Event> events) {

                eventsList= events;

                myAdapter = new EventAdapter(CalendarActivity.this, events);

                recyclerView = findViewById(R.id.listEvents);
                recyclerView.setHasFixedSize(true);

                layoutManager = new LinearLayoutManager(CalendarActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setAdapter(myAdapter);
                calendarGrid.setOnItemClickListener(new CalendarClickListener(model, myAdapter));

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

                DBHelper db= new DBHelper(CalendarActivity.this);
                db.open();
                db.repopulateDB(eventsList);
                db.close();

            }
        }).start();
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
                Intent intent = new Intent(CalendarActivity.this,MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
