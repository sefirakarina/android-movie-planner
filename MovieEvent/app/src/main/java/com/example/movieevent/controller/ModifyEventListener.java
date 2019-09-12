package com.example.movieevent.controller;

import android.app.Activity;
import android.text.Editable;
import android.view.View;

import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.view.ModifyEvent;

public class ModifyEventListener implements View.OnClickListener{

    private String eventId;
    private Editable title;
    private Activity activity;
    private Editable location;
    private Editable venue;
    private Editable startTime,endTime,startDate,endDate;
    private Model model;

    public ModifyEventListener(Activity activity, String eventId, Editable title, Editable location, Editable venue) {
        this.activity = activity;
        this.eventId = eventId;
        this.title = title;
        this.location = location;
        this.venue = venue;
        model = ModelImpl.getSingletonInstance(activity);
    }

    @Override
    public void onClick(View v) {

        //get the user input data from model
        startTime = model.getTempStartTime();
        endTime = model.getTempEndTime();
        startDate = model.getTempStartDate();
        endDate= model.getTempEndDate();

        try{
            model.editEvent(eventId, title.toString(), location.toString(), venue.toString(),startDate, endDate, startTime, endTime);

            Event event = model.getEventById(eventId);

            event.getAttendees().clear();
            event.setAttendees(model.getAllAttendee(eventId));
            if(model.getTempMovie()!=null){
                model.setMovie(model.getTempMovie(), eventId);
            }

            model.resetTempMovie();
            model.clearAttendeeList();

            activity.finish();
        }
        catch (IllegalArgumentException e){
            ((ModifyEvent)activity).showToast(e.getMessage());
        }
        catch (RuntimeException e){
            ((ModifyEvent)activity).showToast(e.getMessage());
        }
    }
}
