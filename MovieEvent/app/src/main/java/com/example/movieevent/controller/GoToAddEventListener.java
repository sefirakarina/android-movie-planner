package com.example.movieevent.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.movieevent.view.AddEvent;
import com.example.movieevent.view.ModifyEvent;

public class GoToAddEventListener implements View.OnClickListener {

    private Context context;

    public GoToAddEventListener(Context context) {
        this.context = context;
    }

    //go to modify event page
    @Override
    public void onClick(View v) {

        Intent editEventIntent = new Intent(context, AddEvent.class);

        editEventIntent.setType("text/plain");

        context.startActivity(editEventIntent);
    }
}
