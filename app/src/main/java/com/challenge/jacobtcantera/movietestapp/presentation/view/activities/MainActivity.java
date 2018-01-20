package com.challenge.jacobtcantera.movietestapp.presentation.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.challenge.jacobtcantera.movietestapp.App;
import com.challenge.jacobtcantera.movietestapp.R;
import com.challenge.jacobtcantera.movietestapp.domain.model.Movie;
import com.challenge.jacobtcantera.movietestapp.presentation.presenter.MainPresenter;
import com.challenge.jacobtcantera.movietestapp.presentation.view.adapters.MovieAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    @Inject MainPresenter presenter;
    @BindView(R.id.rv_movies) RecyclerView rvMovies;
    @BindView(R.id.progress) LinearLayout progress;
    @BindView(R.id.btn_retry) Button retryButton;
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
        addScrollListener();
    }

    private void addScrollListener() {
        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.findLastVisibleItemPosition()
                        == layoutManager.getItemCount() - 1) {
                    presenter.loadMoreMovies();
                }
            }
        });
    }

    @OnClick(R.id.btn_retry)
    public void clickRetry() {
        hideRetryButton();
        presenter.getMovies();
    }

    @Override
    public boolean isProgressShown() {
        return progress.isShown();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showRetryButton() {
        retryButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetryButton() {
        retryButton.setVisibility(View.GONE);
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

    @Override
    public void showError() {
        Toast.makeText(this, R.string.text_error_loading, Toast.LENGTH_SHORT).show();
    }
}
