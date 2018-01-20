package com.challenge.jacobtcantera.movietestapp.rest;

import android.support.annotation.NonNull;

import com.challenge.jacobtcantera.movietestapp.rest.response.MovieResponse;

import io.reactivex.Single;

/**
 * Created by jacob on 20/01/2018.
 */

public interface RestServiceManager {
    Single<MovieResponse> getPopularMoviesCall(@NonNull int page);

    Single<MovieResponse> getSearchByKeywordCall(@NonNull String keyword,
                                               @NonNull int page);
}
