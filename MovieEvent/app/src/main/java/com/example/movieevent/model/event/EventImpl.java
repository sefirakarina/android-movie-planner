package com.example.movieevent.model.event;

import com.example.movieevent.model.movie.Movie;

import java.util.Date;

public class EventImpl extends AbstractEvent{

    public EventImpl(String id, String title, Date startDate, Date endDate, String location, String venue, Movie movie) {
        super(id, title, startDate, endDate, location, venue, movie);
    }

    public EventImpl(String id, String title, Date startDate, Date endDate, String location, String venue) {
        super(id, title, startDate, endDate, location, venue);
    }
}
