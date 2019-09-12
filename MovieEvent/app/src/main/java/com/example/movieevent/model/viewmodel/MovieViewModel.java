package com.example.movieevent.model.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.movie.Movie;

import java.util.ArrayList;

public class MovieViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Movie>> movieLiveData;

    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<Movie>> getMovies(){

        if(movieLiveData == null){
            movieLiveData = new MutableLiveData<>();
            Model model = ModelImpl.getSingletonInstance(getApplication());
            movieLiveData.setValue(model.getAllMovies());
        }
        return movieLiveData;
    }
}
