package com.challenge.jacobtcantera.movietestapp.domain.model;

/**
 * Created by jacob on 20/01/2018.
 */

public class Movie {

    private String title;
    private String overview;
    private String releaseDate;
    private String image;

    public Movie(String title, String overview, String releaseDate, String image) {
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImage() {
        return image;
    }
}
