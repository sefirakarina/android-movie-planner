package com.example.movieevent.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Button;

import com.example.movieevent.R;
import com.example.movieevent.adapter.AttendeeAdapter;
import com.example.movieevent.controller.AddAttendeeListener;
import com.example.movieevent.controller.DeleteAttendeeSwipeListener;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.attendee.Attendee;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.model.viewmodel.AttendeeViewModel;

import java.util.ArrayList;
import java.util.UUID;

public class AddAttendee extends AppCompatActivity {

    Button btnAddAttendee;

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    String eventId;
    Model model = ModelImpl.getSingletonInstance(AddAttendee.this);
    Event event;

    static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        AttendeeViewModel attendeeViewModel = ViewModelProviders.of(this).get(AttendeeViewModel.class);

        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        event = model.getEventById(eventId);

        attendeeViewModel.getAttendees(eventId).observe(this, new Observer<ArrayList<Attendee>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Attendee> attendees) {
                myAdapter = new AttendeeAdapter(attendees);

                recyclerView = findViewById(R.id.listContact);
                recyclerView.setHasFixedSize(true);

                layoutManager = new LinearLayoutManager(AddAttendee.this);
                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setAdapter(myAdapter);

                myAdapter.notifyDataSetChanged();

                ItemTouchHelper touchHelper = new ItemTouchHelper(
                        new DeleteAttendeeSwipeListener(myAdapter, model));
                touchHelper.attachToRecyclerView(recyclerView);
            }
        });

        btnAddAttendee = findViewById(R.id.add_contact_button);
        btnAddAttendee.setOnClickListener(new AddAttendeeListener(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Uri uri = intent.getData();
                String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

                Cursor cursor = getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);

                String id = UUID.randomUUID().toString();

                model.addAttendee(id, name, number, eventId);
            }
        }
    }

}
