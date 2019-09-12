package com.example.movieevent.controller;

import android.app.Activity;
import android.text.Editable;
import android.view.View;

import com.example.movieevent.model.DBHelper;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.view.AddEvent;

public class AddEventListener implements View.OnClickListener{

    private String eventId;
    private Editable title;
    private Activity activity;
    private Editable location;
    private Editable venue;
    private Editable startDate;
    private Editable endDate;
    private Editable startTime;
    private Editable endTime;
    private Model model;
    private DBHelper db;

    public AddEventListener(Activity activity, Editable title, Editable location, Editable venue, String eventId) {
        this.activity = activity;
        this.title = title;
        this.location = location;
        this.venue = venue;
        model = ModelImpl.getSingletonInstance(activity);
        this.eventId = eventId;
    }

    @Override
    public void onClick(View v) {

        startTime = model.getTempStartTime();
        endTime = model.getTempEndTime();
        startDate = model.getTempStartDate();
        endDate= model.getTempEndDate();

        String[] startTimeArr = startTime.toString().split(":");
        String[] endTimeArr = endTime.toString().split(":");
        String[] startDateArr = startDate.toString().split("/");
        String[] endDateArr = endDate.toString().split("/");

        StringBuilder inputStartDate = new StringBuilder();
        StringBuilder inputEndDate = new StringBuilder();

        inputStartDate.append(startDateArr[0] + "/" + startDateArr[1] + "/" + startDateArr[2]
                + " " + startTimeArr[0] + ":" + startTimeArr[1].split(" ")[0]);

        inputEndDate.append(endDateArr[0] + "/" + endDateArr[1] + "/" + endDateArr[2]
                + " " + endTimeArr[0] + ":" + endTimeArr[1].split(" ")[0]);

        try{
            model.addEvent(eventId, title.toString(), venue.toString(), location.toString(), startDate, endDate, startTime, endTime);
//            db.createEventEntry(eventId, title.toString(), location.toString(), venue.toString(), inputStartDate.toString(),
//                                inputEndDate.toString(), model.getTempMovie().getMovieId());


            Event event = model.getEventById(eventId);
            event.setAttendees(model.getAllAttendee(eventId));
            if(model.getTempMovie()!=null){
                model.setMovie(model.getTempMovie(), eventId);
            }

            model.resetTempMovie();
            model.clearAttendeeList();

            activity.finish();
        }
        catch (IllegalArgumentException e){
            ((AddEvent)activity).showToast(e.getMessage());
        }
        catch (RuntimeException e){
            ((AddEvent)activity).showToast(e.getMessage());
        }
    }
}
