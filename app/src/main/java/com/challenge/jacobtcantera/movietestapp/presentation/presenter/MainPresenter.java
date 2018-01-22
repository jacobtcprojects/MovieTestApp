package com.challenge.jacobtcantera.movietestapp.presentation.presenter;

import android.support.annotation.Nullable;

import com.challenge.jacobtcantera.movietestapp.domain.mapper.MovieMapper;
import com.challenge.jacobtcantera.movietestapp.domain.model.Movie;
import com.challenge.jacobtcantera.movietestapp.domain.usecase.GetMoviesByKeywordUseCase;
import com.challenge.jacobtcantera.movietestapp.domain.usecase.GetMoviesUseCase;
import com.challenge.jacobtcantera.movietestapp.domain.usecase.callback.MovieCallback;
import com.challenge.jacobtcantera.movietestapp.rest.response.MovieResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jacob on 20/01/2018.
 */

public class MainPresenter implements MovieCallback {

    public interface View {
        void addMovies(List<Movie> list);
        void showLoadingErrorToast();
        boolean isProgressShown();
        void showProgress();
        void hideProgress();
        void showRetryButton();
        void hideRetryButton();
        void showRecyclerView();
        void hideRecyclerView();
        void showNoMoreResultsErrorToast();
    }

    private static final int FIRST_PAGE = 1;
    private int page;
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
        page = FIRST_PAGE;
    }

    public void destroyView() {
        this.view = null;
    }

    /**
     * Executes a usecase to make the Popular Movies API call.
     * It show the results through a callback.
     */
    public void getPopularMovies() {
        if (view != null && !isProgressShown()) view.showProgress();
        getMoviesUseCase.execute(page, this);
    }

    /**
     * Executes a usecase to make the Search by keyword API call.
     * It show the results through a callback.
     *
     * @param text keyword we want to search
     */
    public void getMoviesByKeyWord(String text) {
        if (view != null && !isProgressShown()) view.showProgress();
        getMoviesByKeywordUseCase.dispose();
        getMoviesByKeywordUseCase.execute(page, text, this);
    }

    // Callback methods
    @Override public void onSuccess(MovieResponse movieResponse) {
        if (movieResponse.getResults().size() > 0) {
            addResultMoviesToList(movieResponse);
        } else {
            showNoMoreResultsError();
        }
    }

    @Override public void onError() {
        showLoadingError();
    }

    /**
     * Pass the API response to the Movie list from the adapter
     *
     * @param movieResponse API response
     */
    private void addResultMoviesToList(MovieResponse movieResponse) {
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
                addPage();
                getPopularMovies();
            }
        }
    }

    public void searchMoreMoviesByKeyword(String text) {
        if (!isProgressShown()) {
            if (view != null) {
                view.showProgress();
                addPage();
                getMoviesByKeyWord(text);
            }
        }
    }

    private void showLoadingError() {
        substractPage(); // We don't want to advance to a new page
        if (view != null) {
            view.hideProgress();
            view.showLoadingErrorToast();
            view.hideRecyclerView();
            view.showRetryButton();
        }
    }

    private void showNoMoreResultsError() {
        substractPage(); // We don't want to advance to a new page
        if (view != null) {
            view.hideProgress();
            view.showNoMoreResultsErrorToast();
        }
    }

    private boolean isProgressShown() {
        return view != null && view.isProgressShown();
    }

    public void resetPage() {
        page = FIRST_PAGE;
    }

    private void addPage() {
        page++;
    }

    private void substractPage() {
        page--;
    }
}