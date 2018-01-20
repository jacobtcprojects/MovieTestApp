package com.challenge.jacobtcantera.movietestapp.presentation.presenter;

import android.support.annotation.Nullable;

import com.challenge.jacobtcantera.movietestapp.domain.model.Movie;
import com.challenge.jacobtcantera.movietestapp.domain.model.mapper.MovieMapper;
import com.challenge.jacobtcantera.movietestapp.rest.MovieApiService;
import com.challenge.jacobtcantera.movietestapp.rest.RestServiceManager;
import com.challenge.jacobtcantera.movietestapp.rest.response.MovieResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jacob on 20/01/2018.
 */

public class MainPresenter {

    private int page;
    private MovieApiService movieApiService;

    public interface View {
        void addMovies(List<Movie> list);
        void showError();
        boolean isProgressShown();
        void showProgress();
        void hideProgress();
        void showRetryButton();
        void hideRetryButton();
    }

    @Nullable private View view;

    private RestServiceManager restServiceManager;
    private MovieMapper movieMapper;

    @Inject
    public MainPresenter(RestServiceManager restServiceManager, MovieMapper movieMapper) {
        this.restServiceManager = restServiceManager;
        this.movieMapper = movieMapper;
    }

    public void initView(View view) {
        this.view = view;
        page = 1;
    }

    public void destroyView() {
        this.view = null;
    }

    public void getMovies() {
        if (view != null && !isProgressShown()) view.showProgress();
        restServiceManager
                .getMovieApiService()
                .getPopularMovies(restServiceManager.getApiKey(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addMovies,
                        throwable -> showError());
    }

    private void addMovies(MovieResponse movieResponse) {
        if (view != null) {
            view.hideProgress();
            view.addMovies(movieMapper
                    .transformList(movieResponse.getResults()));
        }
    }

    public void loadMoreMovies() {
        if (!isProgressShown()) {
            if (view != null) {
                view.showProgress();
                page++;
                getMovies();
            }
        }
    }

    private void showError() {
        if (view != null) {
            view.hideProgress();
            view.showError();
            view.showRetryButton();
        }
    }

    private boolean isProgressShown() {
        if (view != null) {
            return view.isProgressShown();
        }
        return false;
    }
}