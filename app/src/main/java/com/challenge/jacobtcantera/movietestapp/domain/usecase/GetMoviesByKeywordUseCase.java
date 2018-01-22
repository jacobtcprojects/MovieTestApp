package com.challenge.jacobtcantera.movietestapp.domain.usecase;

import com.challenge.jacobtcantera.movietestapp.domain.usecase.callback.MovieCallback;
import com.challenge.jacobtcantera.movietestapp.rest.RestServiceManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jacob on 21/01/2018.
 */

public class GetMoviesByKeywordUseCase {

    private RestServiceManager restServiceManager;
    private Disposable moviesDisposable;

    @Inject public GetMoviesByKeywordUseCase(RestServiceManager restServiceManager) {
        this.restServiceManager = restServiceManager;
    }

    public void execute(int page, String text, final MovieCallback callback) {
        moviesDisposable = restServiceManager
                .getMovieApiService()
                .searchByKeyword(restServiceManager.getApiKey(), text, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movieResponse -> callback.onSuccess(movieResponse),
                        throwable -> callback.onError());
    }

    public void dispose() {
        if (moviesDisposable != null) {
            moviesDisposable.dispose();
        }
    }
}
