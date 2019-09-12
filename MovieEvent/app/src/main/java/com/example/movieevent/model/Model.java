package com.example.movieevent.model;
import android.text.Editable;

import com.example.movieevent.model.attendee.Attendee;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.model.movie.Movie;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public interface Model {

    ArrayList<Event> getAllEvents();
    Event getEventById(String eventId);
    boolean deleteEvent(String eventId);
    void editEvent(String eventId, String title, String location, String venue, Editable startDate,
                   Editable endDate, Editable startTime, Editable endTime);
    void addEvent(String id, String title, String location, String venue, Editable startDate,
                  Editable endDate, Editable startTime, Editable endTime);
    ArrayList<Date>eventDays();

    void setEventOnDate(int day, int month, int year);
    ArrayList<Event> getEventOnDate();
    void clearEventOnDate();

    void setTempStartTime(Editable startTime);
    Editable getTempStartTime();
    void setTempEndTime(Editable endTime);
    Editable getTempEndTime();
    void setTempStartDate(Editable startDate);
    Editable getTempStartDate();
    void setTempEndDate(Editable endDate);
    Editable getTempEndDate();

    ArrayList<Attendee> getAllAttendee(String eventId);
    void addAttendee(String id, String name, String number, String eventId);
    void clearAttendeeList();
    void deleteAttendee(int i);

    void setMovie(Movie movie, String eventId);
    ArrayList<Movie> getAllMovies();
    Movie getTempMovie();
    void setTempMovie(Movie movie);
    void resetTempMovie();
    Movie getMovieById(String movieId);

    void setEvents(ArrayList<Event> events);
    void setMovies(ArrayList<Movie> movies);
    void addPropertyChangeListener(PropertyChangeListener newListener);

    boolean isNetworkConnected();
    void setNetworkConnected(boolean networkConnected);

    Map<String, Integer> getNotifId();

    void addDismissedEventId(String eventId);
    ArrayList<String> getDismissedEventId();

}
