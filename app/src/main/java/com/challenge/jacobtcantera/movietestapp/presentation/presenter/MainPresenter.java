package com.challenge.jacobtcantera.movietestapp.presentation.presenter;

import com.challenge.jacobtcantera.movietestapp.domain.model.Movie;
import com.challenge.jacobtcantera.movietestapp.domain.model.mapper.MovieMapper;
import com.challenge.jacobtcantera.movietestapp.rest.RestServiceManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jacob on 20/01/2018.
 */

public class MainPresenter {
    public interface View {
        void addMovies(List<Movie> list);
    }

    private View view;

    private RestServiceManager restServiceManager;
    private MovieMapper movieMapper;

    @Inject
    public MainPresenter(RestServiceManager restServiceManager, MovieMapper movieMapper) {
        this.restServiceManager = restServiceManager;
        this.movieMapper = movieMapper;
    }

    public void initView(View view) {
        this.view = view;
    }

    public void destroyView() {
        this.view = null;
    }

    public void getMovies() {
        restServiceManager
                .getPopularMoviesCall(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movieResponse -> view.addMovies(movieMapper
                                .transformList(movieResponse.getResults())),
                        Throwable::printStackTrace);

    }
}