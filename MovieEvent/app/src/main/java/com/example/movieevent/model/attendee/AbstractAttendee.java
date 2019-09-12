package com.example.movieevent.model.attendee;

public abstract class AbstractAttendee implements Attendee {

    private String attendeeId;
    private String name;
    private String number;
    private String eventId;

    protected AbstractAttendee(String attendeeId, String name, String number, String eventId){
        this.name = name;
        this.number = number;
        this.attendeeId = attendeeId;
        this.eventId = eventId;
    }

    protected AbstractAttendee(String name, String number){
        this.name = name;
        this.number = number;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getNumber(){
        return this.number;
    }

    @Override
    public String getAttendeeId() {
        return this.attendeeId;
    }

    @Override
    public String getEventId() {
        return this.eventId;
    }

}
