package com.example.movieevent.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.movieevent.R;
import com.example.movieevent.adapter.MovieAdapter;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.model.movie.Movie;
import com.example.movieevent.model.viewmodel.MovieViewModel;

import java.util.ArrayList;

public class SetMovie extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    String eventId;
    Model model = ModelImpl.getSingletonInstance(SetMovie.this);
    Event event;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_movie);

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        event = model.getEventById(eventId);

        movieViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movie> movies) {
                myAdapter = new MovieAdapter(SetMovie.this, movies);

                recyclerView = findViewById(R.id.listMovies);
                recyclerView.setHasFixedSize(true);

                layoutManager = new LinearLayoutManager(SetMovie.this);
                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setAdapter(myAdapter);

                myAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
