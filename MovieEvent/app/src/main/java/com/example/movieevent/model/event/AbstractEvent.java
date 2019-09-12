package com.example.movieevent.model.event;

import com.example.movieevent.model.attendee.Attendee;
import com.example.movieevent.model.movie.Movie;

import java.util.ArrayList;
import java.util.Date;

public class AbstractEvent implements Event{

    private String id;
    private String title;
    private Date startDate;
    private Date endDate;
    private String location;
    private String venue;
    private ArrayList<Attendee> attendees = new ArrayList<>();
    private Movie movie;

    public AbstractEvent(String id, String title, Date startDate, Date endDate, String venue, String location, Movie movie) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.venue = venue;
        this.movie = movie;
    }

    public AbstractEvent(String id, String title, Date startDate, Date endDate, String venue, String location) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.venue = venue;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getVenue() {
        return venue;
    }

    @Override
    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public ArrayList<Attendee> getAttendees(){
        return attendees;
    }

    @Override
    public void setAttendees(ArrayList<Attendee> attendees){

        for(Attendee a:attendees){
            this.attendees.add(a);
        }
    }

    @Override
    public int getTotalAttendees(){
        return attendees.size();
    }

    @Override
    public void setMovie(Movie movie){
        this.movie = movie;
    }

    @Override
    public Movie getMovie(){
        return this.movie;
    }

}
