package com.challenge.jacobtcantera.movietestapp.rest;

import android.support.annotation.NonNull;

import com.challenge.jacobtcantera.movietestapp.rest.response.MovieResponse;

import io.reactivex.Single;

/**
 * Created by jacob on 20/01/2018.
 */

public interface RestServiceManager {
    MovieApiService getMovieApiService();
    String getApiKey();
}
