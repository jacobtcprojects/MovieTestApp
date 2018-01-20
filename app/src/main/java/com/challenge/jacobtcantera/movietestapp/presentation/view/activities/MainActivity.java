package com.challenge.jacobtcantera.movietestapp.presentation.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.challenge.jacobtcantera.movietestapp.App;
import com.challenge.jacobtcantera.movietestapp.R;
import com.challenge.jacobtcantera.movietestapp.domain.model.Movie;
import com.challenge.jacobtcantera.movietestapp.presentation.view.adapters.MovieAdapter;
import com.challenge.jacobtcantera.movietestapp.presentation.presenter.MainPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    @Inject MainPresenter presenter;

    @BindView(R.id.rv_movies) RecyclerView rvMovies;

    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getRestComponent().inject(this);
        ButterKnife.bind(this);
        initViews();
        presenter.initView(this);
        presenter.getMovies();
    }

    private void initViews() {
        adapter = new MovieAdapter();
        rvMovies.setAdapter(adapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
    }

    @Override
    public void addMovies(List<Movie> list) {
        adapter.setMovieList(list);
    }
}
