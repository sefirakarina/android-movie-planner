package com.example.movieevent.model.movie;

public class AbstractMovie implements Movie{

    private String movieId;
    private String name;
    private String year;
    private String image;

    public AbstractMovie(String movieId, String name, String year, String image) {
        this.movieId = movieId;
        this.name = name;
        this.year = year;
        this.image = image;
    }

    @Override
    public String getMovieId() {
        return movieId;
    }

    @Override
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public void setTitle(String name) {
        this.name = name;
    }

    @Override
    public String getYear() {
        return year;
    }

    @Override
    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }
}
