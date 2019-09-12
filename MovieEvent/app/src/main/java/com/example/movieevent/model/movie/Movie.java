package com.example.movieevent.model.movie;

public interface Movie {

    String getMovieId();
    String getTitle();
    String getYear();
    String getImage();

    void setMovieId(String movieId);
    void setTitle(String name);
    void setYear(String year);
    void setImage(String image);

}
