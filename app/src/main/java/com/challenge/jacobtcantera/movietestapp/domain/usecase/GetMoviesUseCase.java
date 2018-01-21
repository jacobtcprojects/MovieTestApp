package com.challenge.jacobtcantera.movietestapp.domain.usecase;

import com.challenge.jacobtcantera.movietestapp.rest.RestServiceManager;
import com.challenge.jacobtcantera.movietestapp.rest.response.MovieResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jacob on 21/01/2018.
 */

public class GetMoviesUseCase {

    public interface Callback {
        void onSuccess(MovieResponse movieResponse);
        void onError();
    }

    private RestServiceManager restServiceManager;

    @Inject public GetMoviesUseCase(RestServiceManager restServiceManager) {
        this.restServiceManager = restServiceManager;
    }

    public void execute(int page, final Callback callback) {
        restServiceManager
                .getMovieApiService()
                .getPopularMovies(restServiceManager.getApiKey(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movieResponse -> callback.onSuccess(movieResponse),
                        throwable -> callback.onError());
    }
}
