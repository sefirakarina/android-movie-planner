package com.example.movieevent.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.movieevent.view.ModifyEvent;

public class EditEventListener implements View.OnClickListener{

    private String eventId;
    private Context context;

    public EditEventListener(Context context, String eventId) {
        this.context = context;
        this.eventId = eventId;
    }

    //go to modify event page
    @Override
    public void onClick(View v) {

        Intent editEventIntent = new Intent(context, ModifyEvent.class);
        editEventIntent.putExtra("eventId", eventId);

        editEventIntent.setType("text/plain");

        context.startActivity(editEventIntent);
    }
}
