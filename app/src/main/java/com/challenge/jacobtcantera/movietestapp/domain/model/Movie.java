package com.challenge.jacobtcantera.movietestapp.domain.model;

/**
 * Created by jacob on 20/01/2018.
 */

public class Movie {
    private static final String BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String COVER_SIZE = "w342";

    private String title;
    private String overview;
    private String year;
    private String image;

    public Movie(String title, String overview, String year, String image) {
        this.title = title;
        this.overview = overview;
        this.year = year;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getYear() {
        return year;
    }

    public String getImage() {
        return image;
    }

    public String getImageUrl() {
        return BASE_URL + COVER_SIZE + image;
    }
}
