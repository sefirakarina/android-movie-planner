package com.example.movieevent.model;

import android.content.Context;
import android.text.Editable;

import com.example.movieevent.model.attendee.Attendee;
import com.example.movieevent.model.attendee.AttendeeImpl;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.model.event.EventImpl;
import com.example.movieevent.model.movie.Movie;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ModelImpl implements Model{

    private ArrayList<Event> eventList = new ArrayList<>();
    private ArrayList<Event> eventOnDate = new ArrayList<>();
    private static Context applicationContext;
    private Editable tempStartTime;
    private Editable tempEndTime;
    private Editable tempStartDate;
    private Editable tempEndDate;
    private boolean isNetworkConnected;

    private Map<String, Integer> notification = new HashMap<>();

    private ArrayList<String> dismissedEvent = new ArrayList<>();

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private ArrayList<Attendee> attendeeList = new ArrayList<>();
    private ArrayList<Movie> movieList = new ArrayList<>();
    private Movie movie;

    private SimpleDateFormat minute = new SimpleDateFormat("mm");
    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private ModelImpl() { }

    private static class LazyHolder {
        static final Model INSTANCE = new ModelImpl();
    }

    public static Model getSingletonInstance(Context appContext) {
        if (applicationContext == null) {
            applicationContext = appContext;
            LoadFiles loadFiles = new LoadFiles(appContext);
            loadFiles.execute();
            LoadSharedPref loadSharedPref = LoadSharedPref.getSingletonInstance(appContext);
            loadSharedPref.execute();
        }
        return LazyHolder.INSTANCE;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener newListener) {
        pcs.addPropertyChangeListener(newListener);
    }

    @Override
    public ArrayList<Event> getAllEvents() {
        return eventList;
    }

    @Override
    public Event getEventById(String eventId) {
        Iterator<Event> iterator = eventList.iterator();
        int i = 0;

        while (iterator.hasNext())
        {
            Event event = iterator.next();

            if(event.getId().equals(eventId))
                return eventList.get(i);
            else{
                i++;
                continue;
            }
        }

        return null;
    }

    @Override
    public boolean deleteEvent(String eventId) {
        if(eventList.contains(getEventById(eventId))){
            eventList.remove(getEventById(eventId));
            return true;
        }
        else
            return false;
    }

    @Override
    public void editEvent(String eventId, String title, String location, String venue,
                          Editable startDate, Editable endDate, Editable startTime, Editable endTime) {

        Date today = new Date();
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(today);

        Date newStartDate = null;
        Date newEndDate = null;


        if(checkIfNull(title, location, venue, startDate, endDate))
        {
            throw new RuntimeException("Please fill all the fields.");
        }
        else{
            // prepare the date format to be stored
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

            try {
                newStartDate = formatter.parse(inputStartDate.toString());
                newEndDate = formatter.parse(inputEndDate.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int day = Integer.parseInt(startDateArr[0]);
            int month = Integer.parseInt(startDateArr[1]);
            int year = Integer.parseInt(startDateArr[2]);
            int endDay = Integer.parseInt(endDateArr[0]);
            int endMonth = Integer.parseInt(endDateArr[1]);
            int endYear = Integer.parseInt(endDateArr[2]);
            int hour = Integer.parseInt(startTimeArr[0]);
            int minute = Integer.parseInt(this.minute.format(newStartDate));
            int endHour = Integer.parseInt(endTimeArr[0]);
            int endMinute = Integer.parseInt(this.minute.format(newEndDate));

            if(checkDateAndTime(day, month, year, endDay, endMonth, endYear, hour, minute, endHour,
                    endMinute))
            {

                throw new IllegalArgumentException("Please enter a valid date and time.");
            }
            else{
                Event tempEvent = getEventById(eventId);
                Event event = getEventById(eventId);
                event.setTitle(title);
                event.setEndDate(newEndDate);
                event.setStartDate(newStartDate);
                event.setLocation(location);
                event.setVenue(venue);
                pcs.firePropertyChange("changing event", tempEvent, event);

            }
        }


    }

    @Override
    public void addEvent(String id,String title, String location, String venue,
                         Editable startDate, Editable endDate, Editable startTime, Editable endTime) {

        Date today = new Date();
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(today);

        Date newStartDate = null;
        Date newEndDate = null;

        //all fields must be filled
        if(checkIfNull(title, location,venue,startDate, endDate))
        {
            throw new RuntimeException("Please fill all the fields.");
        }
        else{
            // prepare the date format to be stored
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

            try {
                newStartDate = formatter.parse(inputStartDate.toString());
                newEndDate = formatter.parse(inputEndDate.toString());

            } catch (ParseException e) {
                e.printStackTrace();
            }

            int day = Integer.parseInt(startDateArr[0]);
            int month = Integer.parseInt(startDateArr[1]);
            int year = Integer.parseInt(startDateArr[2]);
            int endDay = Integer.parseInt(endDateArr[0]);
            int endMonth = Integer.parseInt(endDateArr[1]);
            int endYear = Integer.parseInt(endDateArr[2]);
            int hour = Integer.parseInt(startTimeArr[0]);
            int minute = Integer.parseInt(this.minute.format(newStartDate));
            int endHour = Integer.parseInt(endTimeArr[0]);
            int endMinute = Integer.parseInt(this.minute.format(newEndDate));

            // the event date can't be before today
            if(checkDateAndTime(day, month, year, endDay, endMonth, endYear, hour, minute,
                    endHour, endMinute))
            {
                throw new IllegalArgumentException("Please enter a valid date and time.");
            }
            else{
                Event event = new EventImpl(id,title,newStartDate,newEndDate,location,venue);
                eventList.add(event);
                pcs.firePropertyChange("changing event", false, event);
            }

        }

    }

    @Override
    public ArrayList<Date> eventDays() {

        ArrayList<Date>temp = new ArrayList<>();

        Iterator<Event>iterator = eventList.iterator();
        while(iterator.hasNext()){
            Event event = iterator.next();
            temp.add(event.getStartDate());
        }

        return temp;
    }

    @Override
    public void setEventOnDate(int day, int month, int year) {
        eventOnDate.clear();

        for(Event event : eventList){

            SimpleDateFormat year1 = new SimpleDateFormat("dd");
            SimpleDateFormat year2 = new SimpleDateFormat("MM");
            SimpleDateFormat year3 = new SimpleDateFormat("yyyy");


            int dd = Integer.parseInt(year1.format(event.getStartDate()));
            int mm = Integer.parseInt(year2.format(event.getStartDate()));
            int yyyy = Integer.parseInt(year3.format(event.getStartDate()));

            if(day == dd && month == mm && year == yyyy){
                eventOnDate.add(event);
            }
        }
    }

    @Override
    public void setTempStartTime(Editable startTime) {
        this.tempStartTime = startTime;
    }

    @Override
    public Editable getTempStartTime() {
        return this.tempStartTime;
    }

    @Override
    public void setTempEndTime(Editable endTime) {
        this.tempEndTime = endTime;
    }

    @Override
    public Editable getTempEndTime() {
        return this.tempEndTime;
    }

    @Override
    public void setTempStartDate(Editable startDate) {
        this.tempStartDate = startDate;
    }

    @Override
    public Editable getTempStartDate() {
        return this.tempStartDate;
    }

    @Override
    public void setTempEndDate(Editable endDate) {
        this.tempEndDate= endDate;
    }

    @Override
    public Editable getTempEndDate() {
        return this.tempEndDate;
    }

    @Override
    public ArrayList<Event> getEventOnDate(){
        return eventOnDate;
    }

    private void getEvents(ArrayList<Event> events){

        this.eventList = events;


    }

    @Override
    public void setEvents(ArrayList<Event> events) {
        this.eventList = events;
        pcs.firePropertyChange("changing event", false, events);
    }

    @Override
    public void setMovies(ArrayList<Movie> movies) {
        this.movieList = movies;
    }

    @Override
    public ArrayList<Attendee> getAllAttendee(String eventId){

        Event event = getEventById(eventId);

        if(event == null){
            return attendeeList;
        }
        else if(event.getAttendees().isEmpty() ){
            return attendeeList;
        }
        else if(!event.getAttendees().isEmpty() && attendeeList.isEmpty()){
            for(Attendee a:event.getAttendees()){
                attendeeList.add(a);
            }
            return attendeeList;
        }
        else{
            return attendeeList;
        }
    }

    @Override
    public void addAttendee(String id, String name, String number, String eventId){

        for(Attendee attendee : attendeeList){
            if(attendee.getName().equals(name) && attendee.getNumber().equals(number)){
                return;
            }
        }
        attendeeList.add(new AttendeeImpl(id, name, number, eventId));
    }

    @Override
    public void deleteAttendee(int i){
        attendeeList.remove(i);
    }

    @Override
    public void clearAttendeeList(){
        this.attendeeList.clear();
    }

    @Override
    public void setMovie(Movie movie, String eventId){
        Event oldEvent = getEventById(eventId);
        Event event = getEventById(eventId);
        event.setMovie(movie);
        pcs.firePropertyChange("changing event", oldEvent, event);


    }

    @Override
    public Movie getTempMovie(){
        return movie;
    }

    @Override
    public void setTempMovie(Movie movie){
        this.movie = movie;
    }

    @Override
    public void resetTempMovie(){
        this.movie = null;
    }

    @Override
    public ArrayList<Movie> getAllMovies(){
        return this.movieList;
    }

    @Override
    public void clearEventOnDate(){
        this.eventOnDate.clear();
    }

    @Override
    public Movie getMovieById(String movieId) {
        Iterator<Movie> iterator = movieList.iterator();
        int i = 0;

        while (iterator.hasNext())
        {
            Movie movie = iterator.next();

            if(movie.getMovieId().equals(movieId))
                return movieList.get(i);
            else{
                i++;
                continue;
            }
        }

        return null;
    }

    private boolean checkIfNull(String title, String location, String venue, Editable startDate,
                                Editable endDate){


        if(title.trim().isEmpty() || title.trim() ==null || location.trim().isEmpty() ||
                location.trim() ==null || venue.trim().isEmpty() || venue.trim() ==null ||
                startDate == null || endDate ==null){
            return true;
        }

        return false;
    }

    private boolean checkDateAndTime(int day, int month, int year, int endDay, int endMonth,
                                     int endYear, int hour, int minute, int endHour, int endMinute){

        Date today = new Date();
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(today);

        if(day < calendarToday.get(Calendar.DATE) && month <= calendarToday.get(Calendar.MONTH)+1
                && year <= calendarToday.get(Calendar.YEAR) || endDay < day && endMonth
                <= month && endYear <= year){
            return true;
        }
        else if ((day == endDay && endHour < hour && endMonth == month && year == endYear)
                || (day == endDay && endHour == hour && endMinute <= minute && endMonth == month
                && year == endYear)){
            return true;
        }

        return false;
    }

    @Override
    public boolean isNetworkConnected() {
        return isNetworkConnected;
    }
    @Override
    public void setNetworkConnected(boolean networkConnected) {
        isNetworkConnected = networkConnected;
    }

    @Override
    public Map<String, Integer> getNotifId(){
        return this.notification;
    }

    @Override
    public void addDismissedEventId(String eventId){
        this.dismissedEvent.add(eventId);
    }

    @Override
    public ArrayList<String> getDismissedEventId(){
        return this.dismissedEvent;
    }
}