package com.example.movieevent.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieevent.R;
import com.example.movieevent.controller.SetMovieListener;
import com.example.movieevent.model.movie.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter <MovieAdapter.ViewHolder>{

    private ArrayList<Movie> movies;
    private Activity activity;


    public MovieAdapter(Activity activity, ArrayList<Movie> movies){
        this.activity = activity;
        this.movies = movies;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView movieTitle, movieYear;
        ImageView movieImage;

        public ViewHolder(@NonNull View view){
            super(view);
            movieTitle = view.findViewById(R.id.movie_title);
            movieYear = view.findViewById(R.id.movie_year);
            movieImage = view.findViewById(R.id.movie_image);
        }
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_movies,
                viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = movies.get(i);

        Uri uri = Uri.parse("android.resource://"+activity.getPackageName()+"/drawable/"+movie.getImage().toLowerCase());

        if(movie != null){
            viewHolder.itemView.setTag(movie);

            viewHolder.movieTitle.setText(movie.getTitle());
            viewHolder.movieYear.setText(movie.getYear());
            viewHolder.movieImage.setImageURI(uri);

            viewHolder.itemView.setOnClickListener(new SetMovieListener(activity, movie));
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
