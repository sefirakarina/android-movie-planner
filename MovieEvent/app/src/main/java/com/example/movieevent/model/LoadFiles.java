package com.example.movieevent.model;

import android.content.Context;
import android.os.AsyncTask;

import com.example.movieevent.model.attendee.Attendee;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.model.movie.Movie;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class LoadFiles extends AsyncTask<Void, Void, Void> {


    private final String TAG = getClass().getSimpleName();
    private Context context;
    private DBHelper dbHelper;
    private Model model;
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Movie> movies =  new ArrayList<>();
    private ArrayList<Attendee> attendeeList = new ArrayList<>();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public LoadFiles(Context context) {
        this.context = context;
        model = ModelImpl.getSingletonInstance(context);
        dbHelper = new DBHelper(context);
    }

    public void loadEvents() {

        BufferedReader br = null;

        try {

            String sCurrentLine;
            int i = 0;
            ArrayList<ArrayList<String>> eventsFromFile = new ArrayList<>();

            br = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("events.txt"),
                            "UTF-8"));

            while ((sCurrentLine = br.readLine()) != null) {
                ArrayList<String> words = new ArrayList<String>();

                if(i > 1){
                    for(String data : sCurrentLine.split("\",\"")){
                        data = data.replaceAll("\"", "");
                        words.add(data);
                    }
                    eventsFromFile.add(words);
                }
                i++;
            }
            dbHelper.open();

            for (ArrayList<String> eachEvent : eventsFromFile){

                dbHelper.createEventEntry(eachEvent.get(0), eachEvent.get(1),eachEvent.get(2),
                        eachEvent.get(3), eachEvent.get(4),eachEvent.get(5), null);
            }
            for(Event eventFromDb : dbHelper.getEventData()){

                events.add(eventFromDb);

                for(Attendee attendee : dbHelper.getEventAttendee(eventFromDb.getId())){

                    attendeeList.add(attendee);
                }
                eventFromDb.setAttendees(attendeeList);

                attendeeList.clear();
            }
            dbHelper.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void loadMovies() {

        BufferedReader br = null;

        try{
            String currentLine;
            int i = 0;
            ArrayList<ArrayList<String>> moviesFromFile = new ArrayList<>();

            br = new BufferedReader(new InputStreamReader(context.getAssets().open(
                    "movies.txt"),
                    StandardCharsets.UTF_8));

            while((currentLine = br.readLine()) != null){
                ArrayList<String> words = new ArrayList<>();

                if(i > 0){
                    for(String data : currentLine.split("\",\"")){
                        data = data.replaceAll("\"","");
                        words.add(data);
                    }
                    moviesFromFile.add(words);
                }
                i++;
            }
            dbHelper.open();
            for(ArrayList<String> eachMovie : moviesFromFile){
                ArrayList<String> photoName = new ArrayList<>();
                for(String photoRawName : eachMovie.get(3).split(Pattern.quote("."))){
                    photoName.add(photoRawName);
                }

                dbHelper.createMovieEntry(eachMovie.get(0), eachMovie.get(1),eachMovie.get(2),
                        photoName.get(0));
            }
            for(Movie movieFromDb : dbHelper.getMovieData()){
                movies.add(movieFromDb);
            }

            dbHelper.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... unused)
    {
        loadMovies();
        loadEvents();
        return null;
    }

    @Override
    public void onPostExecute(Void result)
    {
        model.setEvents(events);
        model.setMovies(movies);
        pcs.firePropertyChange("finishLoadingFile", null, null);
    }


    public void addPropertyChangeListener(PropertyChangeListener newListener) {
        pcs.addPropertyChangeListener(newListener);
    }
}
