package com.example.movieevent.model.event;

import com.example.movieevent.model.attendee.Attendee;
import com.example.movieevent.model.movie.Movie;

import java.util.ArrayList;
import java.util.Date;

public interface Event {

    String getId();
    String getTitle();
    Date getStartDate();
    Date getEndDate();
    String getLocation();
    String getVenue();

    void setId(String id);
    void setTitle(String title);
    void setStartDate(Date startDate);
    void setEndDate(Date endDate);
    void setLocation(String location);
    void setVenue(String venue);

    ArrayList<Attendee> getAttendees();
    void setAttendees(ArrayList<Attendee> attendees);
    int getTotalAttendees();

    void setMovie(Movie movie);
    Movie getMovie();

}
