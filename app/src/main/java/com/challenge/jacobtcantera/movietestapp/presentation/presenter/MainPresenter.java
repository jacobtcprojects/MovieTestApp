package com.challenge.jacobtcantera.movietestapp.presentation.presenter;

import android.support.annotation.Nullable;

import com.challenge.jacobtcantera.movietestapp.domain.usecase.GetMoviesUseCase;
import com.challenge.jacobtcantera.movietestapp.domain.model.Movie;
import com.challenge.jacobtcantera.movietestapp.domain.model.mapper.MovieMapper;
import com.challenge.jacobtcantera.movietestapp.rest.MovieApiService;
import com.challenge.jacobtcantera.movietestapp.rest.RestServiceManager;
import com.challenge.jacobtcantera.movietestapp.rest.response.MovieResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jacob on 20/01/2018.
 */

public class MainPresenter implements GetMoviesUseCase.Callback {

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
        void showRecyclerView();
        void hideRecyclerView();
    }

    @Nullable private View view;

    private MovieMapper movieMapper;
    private GetMoviesUseCase getMoviesUseCase;

    @Inject
    public MainPresenter(MovieMapper movieMapper, GetMoviesUseCase getMoviesUseCase) {
        this.movieMapper = movieMapper;
        this.getMoviesUseCase = getMoviesUseCase;
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
        getMoviesUseCase.execute(page, this);
    }

    @Override public void onSuccess(MovieResponse movieResponse) {
        addMovies(movieResponse);
    }

    @Override public void onError() {
        showError();
    }

    private void addMovies(MovieResponse movieResponse) {
        if (view != null) {
            view.showRecyclerView();
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
            view.hideRecyclerView();
            view.showRetryButton();
        }
    }

    private boolean isProgressShown() {
        return view != null && view.isProgressShown();
    }
}