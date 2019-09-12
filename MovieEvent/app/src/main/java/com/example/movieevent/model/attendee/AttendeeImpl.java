package com.example.movieevent.model.attendee;

public class AttendeeImpl extends AbstractAttendee {

    public AttendeeImpl(String name, String number){
        super(name, number);
    }

    public AttendeeImpl(String attendeeId, String name, String number, String eventId){
        super(attendeeId, name, number,eventId);
    }
}
