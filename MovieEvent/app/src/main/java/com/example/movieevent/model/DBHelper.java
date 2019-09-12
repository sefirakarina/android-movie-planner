package com.example.movieevent.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movieevent.model.attendee.Attendee;
import com.example.movieevent.model.attendee.AttendeeImpl;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.model.event.EventImpl;
import com.example.movieevent.model.movie.Movie;
import com.example.movieevent.model.movie.MovieImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    public static final String KEY_EVENTID = "event_id";
    public static final String KEY_EVENTTITLE = "_title";
    public static final String KEY_VENUE = "_venue";
    public static final String KEY_LOCATION = "_location";
    public static final String KEY_STARTDATE = "start_date";
    public static final String KEY_ENDDATE = "end_date";
    public static final String KEY_MOVIEID = "movie_id";
    public static final String KEY_ATTENDEEID = "attendee_id";

    public static final String KEY_MOVIETITLE = "movie_title";
    public static final String KEY_YEAR = "_year";
    public static final String KEY_IMG = "_img";

    public static final String KEY_NAME = "_name";
    public static final String KEY_PHONE = "_phone";

    private static final String DATABASE_NAME = "MovieEvent";
    private final String EVENT_TABLE = "EventTable";
    private final String MOVIE_TABLE = "MovieTable";
    private final String ATTENDEE_TABLE = "AttendeeTable";
    private static final int DATABASE_VERSION = 32;



    SimpleDateFormat year1 = new SimpleDateFormat("dd");
    SimpleDateFormat year2 = new SimpleDateFormat("MM");
    SimpleDateFormat year3 = new SimpleDateFormat("yyyy");
    SimpleDateFormat time1 = new SimpleDateFormat("HH");
    SimpleDateFormat time2 = new SimpleDateFormat("mm");
    SimpleDateFormat time3 = new SimpleDateFormat("ss");

    SQLiteDatabase db;


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String movieSqlCode = "CREATE TABLE " + MOVIE_TABLE + " (" +
                KEY_MOVIEID + " TEXT PRIMARY KEY, " +
                KEY_MOVIETITLE + " TEXT NOT NULL, " +
                KEY_YEAR + " TEXT NOT NULL," +
                KEY_IMG + " TEXT );";

        String eventSqlCode = "CREATE TABLE " +EVENT_TABLE + " (" +
                KEY_EVENTID + " TEXT PRIMARY KEY, " +
                KEY_EVENTTITLE + " TEXT NOT NULL, " +
                KEY_VENUE + " TEXT NOT NULL," +
                KEY_LOCATION + " TEXT NOT NULL," +
                KEY_STARTDATE + " TEXT NOT NULL," +
                KEY_ENDDATE + " TEXT NOT NULL," +
                KEY_MOVIEID +" TEXT REFERENCES " + MOVIE_TABLE+ ");";

        String attendeeSqlCode = "CREATE TABLE " +ATTENDEE_TABLE + " (" +
                KEY_ATTENDEEID + " TEXT PRIMARY KEY, " +
                KEY_NAME + " TEXT NOT NULL, " +
                KEY_PHONE + " TEXT NOT NULL," +
                KEY_EVENTID +" TEXT REFERENCES " + EVENT_TABLE+ ");";

        db.execSQL(movieSqlCode);
        db.execSQL(eventSqlCode);
        db.execSQL(attendeeSqlCode);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ATTENDEE_TABLE);
        onCreate(db);
    }

    public DBHelper open() throws SQLException {

        db = this.getWritableDatabase();

        return this;
    }

    public void close(){

        db.close();
    }

    public void clearRows(){

        db.delete(EVENT_TABLE, null, null);
        db.delete(ATTENDEE_TABLE, null, null);
    }

    public void createEventEntry(String id, String title, String startDate, String endDate, String venue, String location, String movieID){

        if(!isValueInDB(id, KEY_EVENTID, EVENT_TABLE)){

            ContentValues cv = new ContentValues();
            cv.put(KEY_EVENTID , id);
            cv.put(KEY_EVENTTITLE, title);
            cv.put(KEY_STARTDATE, startDate);
            cv.put(KEY_ENDDATE, endDate);
            cv.put(KEY_VENUE,venue);
            cv.put(KEY_LOCATION, location);
            cv.put(KEY_MOVIEID, movieID);

            db.insert(EVENT_TABLE, null, cv);
        }
    }

    public ArrayList<Event> getEventData(){

        String[] columns = new String[]{KEY_EVENTID, KEY_EVENTTITLE, KEY_STARTDATE, KEY_ENDDATE, KEY_VENUE, KEY_LOCATION, KEY_MOVIEID};

        Cursor c = db.query(EVENT_TABLE, columns, null, null, null, null, null);

        int eventId = c.getColumnIndex(KEY_EVENTID);
        int title = c.getColumnIndex(KEY_EVENTTITLE);
        int startDate = c.getColumnIndex(KEY_STARTDATE);
        int endDate = c.getColumnIndex(KEY_ENDDATE);
        int venue = c.getColumnIndex(KEY_VENUE);
        int location = c.getColumnIndex(KEY_LOCATION);
        int movieId = c.getColumnIndex(KEY_MOVIEID);

        ArrayList<Event> eventList = new ArrayList<>();

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date newStartDate = null;
        Date newEndDate = null;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            try {
                newStartDate = formatter.parse(c.getString(startDate));
                newEndDate = formatter.parse(c.getString(endDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(c.getString(movieId) == null){
                eventList.add(new EventImpl(c.getString(eventId),c.getString(title),newStartDate,newEndDate,
                        c.getString(venue),c.getString(location)));
            }
            else {
                Movie movie = getMovieById(c.getString(movieId));

                eventList.add(new EventImpl(c.getString(eventId),c.getString(title),newStartDate,newEndDate,
                        c.getString(venue),c.getString(location), movie));
            }

        }

        c.close();
        return eventList;
    }

    public void createMovieEntry(String id, String title, String year, String img){

        if(!isValueInDB(id, KEY_MOVIEID, MOVIE_TABLE)){

            ContentValues cv = new ContentValues();
            cv.put(KEY_MOVIEID , id);
            cv.put(KEY_IMG, img);
            cv.put(KEY_MOVIETITLE, title);
            cv.put(KEY_YEAR,year);

            db.insert(MOVIE_TABLE, null, cv);
        }
    }

    public ArrayList<Movie> getMovieData(){

        ArrayList<Movie> movieList = new ArrayList<>();

        String[] columns = new String[]{KEY_MOVIEID, KEY_MOVIETITLE, KEY_YEAR, KEY_IMG};

        Cursor c = db.query(MOVIE_TABLE, columns, null, null, null, null, null);

        int movieId = c.getColumnIndex(KEY_MOVIEID);
        int title = c.getColumnIndex(KEY_MOVIETITLE);
        int year = c.getColumnIndex(KEY_YEAR);
        int img = c.getColumnIndex(KEY_IMG);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            movieList.add(new MovieImpl(c.getString(movieId), c.getString(title), c.getString(year),
                    c.getString(img)));
        }

        c.close();
        return movieList;
    }


    public Movie getMovieById(String id){

        //SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + MOVIE_TABLE + " WHERE " + KEY_MOVIEID + " = " + id;
        Cursor c = db.rawQuery(query, null);

        if (c != null){

            if (c.moveToFirst()){
                int movieId = c.getColumnIndex(KEY_MOVIEID);
                int title = c.getColumnIndex(KEY_MOVIETITLE);
                int year = c.getColumnIndex(KEY_YEAR);
                int img = c.getColumnIndex(KEY_IMG);

                Movie movie = new MovieImpl(c.getString(movieId), c.getString(title), c.getString(year),
                        c.getString(img));

                return movie;
            }
        }
        return null;
    }

    public void createAttendeeEntry(ArrayList<Attendee> attendees){

        ContentValues cv = new ContentValues();

        for(Attendee attendee : attendees) {

            String id = attendee.getAttendeeId();
            String name = attendee.getName();
            String phone = attendee.getNumber();
            String eventId = attendee.getEventId();

            cv.put(KEY_EVENTID , id);
            cv.put(KEY_NAME, name);
            cv.put(KEY_PHONE, phone);
            cv.put(KEY_EVENTID,eventId);

            db.insert(ATTENDEE_TABLE, null, cv);
        }

    }

    public ArrayList<Attendee> getEventAttendee(String eventId){

        ArrayList<Attendee> attendeeList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + ATTENDEE_TABLE + " WHERE " + KEY_EVENTID + " = " + "'" +eventId +"'";
        Cursor c = db.rawQuery(query, null);

        if (c != null){

            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                int attendeeId = c.getColumnIndex(KEY_ATTENDEEID);
                int name = c.getColumnIndex(KEY_NAME);
                int phone = c.getColumnIndex(KEY_PHONE);

                Attendee attendee = new AttendeeImpl(c.getString(attendeeId), c.getString(name), c.getString(phone), eventId);
                attendeeList.add(attendee);
            }
            return attendeeList;
        }
        return null;
    }



    public boolean isValueInDB(String id, String col, String tableName){

        String query = "SELECT * FROM " + tableName + " WHERE " + col + " = '" + id + "'";
        Cursor c = db.rawQuery(query, null);

        if(c.getCount() <= 0){
            c.close();
            return false;
        }
        c.close();
        return true;
    }

    public void repopulateDB(ArrayList<Event> eventList){

        clearRows();

        for(Event event : eventList){

            int startDay = Integer.parseInt(year1.format(event.getStartDate()));
            int startMonth = Integer.parseInt(year2.format(event.getStartDate()));
            int startYear = Integer.parseInt(year3.format(event.getStartDate()));
            int startHour = Integer.parseInt(time1.format(event.getStartDate()));
            int startMinute = Integer.parseInt(time2.format(event.getStartDate()));
            int startSecond = Integer.parseInt(time3.format(event.getStartDate()));

            int endDay = Integer.parseInt(year1.format(event.getEndDate()));
            int endMonth = Integer.parseInt(year2.format(event.getEndDate()));
            int endYear = Integer.parseInt(year3.format(event.getEndDate()));
            int endHour = Integer.parseInt(time1.format(event.getEndDate()));
            int endMinute = Integer.parseInt(time2.format(event.getEndDate()));
            int endSecond = Integer.parseInt(time3.format(event.getEndDate()));

            StringBuilder inputStartDate = new StringBuilder();
            StringBuilder inputEndDate = new StringBuilder();

            inputStartDate.append(startDay + "/" + startMonth + "/" + startYear + " " + startHour + ":" + startMinute+ ":" + startSecond);
            inputEndDate.append(endDay + "/" + endMonth + "/" + endYear + " " + endHour + ":" + endMinute + ":" + endSecond);

            if(event.getMovie() != null){

                createEventEntry(event.getId(), event.getTitle(),inputStartDate.toString(), inputEndDate.toString(),
                        event.getVenue(), event.getLocation(), event.getMovie().getMovieId());
            }
            else {

                createEventEntry(event.getId(), event.getTitle(),inputStartDate.toString(), inputEndDate.toString(),
                        event.getVenue(), event.getLocation(), null);
            }

            if(event.getAttendees().size() > 0){

                createAttendeeEntry(event.getAttendees());
            }
        }
    }
}
