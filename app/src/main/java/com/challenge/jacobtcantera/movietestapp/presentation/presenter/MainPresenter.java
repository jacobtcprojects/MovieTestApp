package com.challenge.jacobtcantera.movietestapp.presentation.presenter;

import android.support.annotation.Nullable;

import com.challenge.jacobtcantera.movietestapp.domain.model.Movie;
import com.challenge.jacobtcantera.movietestapp.domain.model.mapper.MovieMapper;
import com.challenge.jacobtcantera.movietestapp.domain.usecase.GetMoviesByKeywordUseCase;
import com.challenge.jacobtcantera.movietestapp.domain.usecase.GetMoviesUseCase;
import com.challenge.jacobtcantera.movietestapp.domain.usecase.MovieCallback;
import com.challenge.jacobtcantera.movietestapp.rest.response.MovieResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jacob on 20/01/2018.
 */

public class MainPresenter implements MovieCallback {

    private int page;

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
    private GetMoviesByKeywordUseCase getMoviesByKeywordUseCase;

    @Inject
    public MainPresenter(MovieMapper movieMapper,
                         GetMoviesUseCase getMoviesUseCase,
                         GetMoviesByKeywordUseCase getMoviesByKeywordUseCase) {
        this.movieMapper = movieMapper;
        this.getMoviesUseCase = getMoviesUseCase;
        this.getMoviesByKeywordUseCase = getMoviesByKeywordUseCase;
    }

    public void initView(View view) {
        this.view = view;
        page = 1;
    }

    public void destroyView() {
        this.view = null;
    }

    public void getPopularMovies() {
        if (view != null && !isProgressShown()) view.showProgress();
        getMoviesUseCase.execute(page, this);
    }

    public void getMoviesByKeyWord(String text){
        getMoviesByKeywordUseCase.dispose();
        getMoviesByKeywordUseCase.execute(page, text, this);
    }

    @Override public void onSuccess(MovieResponse movieResponse) {
        addMovies(movieResponse);
    }

    @Override public void onError() {
        page--;
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

    public void loadMorePopularMovies() {
        if (!isProgressShown()) {
            if (view != null) {
                view.showProgress();
                page++;
                getPopularMovies();
            }
        }
    }
    public void searchMoreMoviesByKeyword(String text) {
        if (!isProgressShown()) {
            if (view != null) {
                view.showProgress();
                page++;
                getMoviesByKeyWord(text);
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

    public void resetPage() {
        this.page = 1;
    }
}