package com.challenge.jacobtcantera.movietestapp.domain.usecase.callback;

import com.challenge.jacobtcantera.movietestapp.rest.response.MovieResponse;

/**
 * Created by jacob on 21/01/2018.
 */

public interface MovieCallback {
    void onSuccess(MovieResponse movieResponse);
    void onError();
}
