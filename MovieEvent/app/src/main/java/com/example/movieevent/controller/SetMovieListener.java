package com.example.movieevent.controller;

import android.app.Activity;
import android.view.View;

import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.movie.Movie;

public class SetMovieListener implements View.OnClickListener {

    private Movie movie;
    private Model model;
    private Activity activity;

    public SetMovieListener(Activity activity, Movie movie){

        this.activity = activity;
        this.movie = movie;
        model = ModelImpl.getSingletonInstance(activity);
    }

    @Override
    public void onClick(View v) {
        model.setTempMovie(movie);
        activity.finish();
    }
}
