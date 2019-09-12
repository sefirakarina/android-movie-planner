package com.example.movieevent.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.movieevent.view.AddAttendee;

public class GoToAttendeesListener implements View.OnClickListener {

    private Context context;
    private String eventId;

    public GoToAttendeesListener(Context context, String eventId) {
        this.context = context;
        this.eventId = eventId;
    }

    @Override
    public void onClick(View v){

        Intent addAttendee = new Intent(context, AddAttendee.class);
        addAttendee.putExtra("eventId", eventId);

        addAttendee.setType("text/plain");

        context.startActivity(addAttendee);
    }
}
