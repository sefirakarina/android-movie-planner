package com.example.movieevent.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.movieevent.view.SetMovie;

public class GoToMoviesListener implements View.OnClickListener {

    private Context context;
    private String eventId;

    public GoToMoviesListener(Context context, String eventId) {
        this.context = context;
        this.eventId = eventId;
    }

    @Override
    public void onClick(View v) {
        Intent addAttendee = new Intent(context, SetMovie.class);
        addAttendee.putExtra("eventId", eventId);

        addAttendee.setType("text/plain");

        context.startActivity(addAttendee);
    }
}
