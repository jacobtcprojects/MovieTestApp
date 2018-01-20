package com.challenge.jacobtcantera.movietestapp.domain.model.mapper;

import com.challenge.jacobtcantera.movietestapp.domain.model.Movie;
import com.challenge.jacobtcantera.movietestapp.rest.response.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by jacob on 20/01/2018.
 */

public class MovieMapper {

    @Inject
    public MovieMapper() {}

    public Movie transform(MovieResponse.Result movieResult) {
        Movie movie = null;
        if (movieResult != null) {
            movie = new Movie(
                    movieResult.getTitle(),
                    movieResult.getOverview(),
                    movieResult.getReleaseDate(),
                    movieResult.getPosterPath());
        }
        return movie;
    }

    public List<Movie> transformList(List<MovieResponse.Result> moviesList) {
        List<Movie> movies = new ArrayList<>();
        for (MovieResponse.Result movieResult : moviesList) {
            if (movieResult != null) {
                movies.add(transform(movieResult));
            }
        }
        return movies;
    }
}
