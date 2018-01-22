package com.challenge.jacobtcantera.movietestapp.presentation.view.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    public static final int DELAY_MILLIS = 500;
    @Inject MainPresenter presenter;
    @BindView(R.id.rv_movies) RecyclerView rvMovies;
    @BindView(R.id.progress) LinearLayout progress;
    @BindView(R.id.btn_retry) Button retryButton;
    @BindView(R.id.edt_search) EditText editTextSearch;
    private MovieAdapter adapter;
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable workRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getRestComponent().inject(this);
        ButterKnife.bind(this);
        initViews();
        presenter.initView(this);
        presenter.getPopularMovies();
    }

    private void initViews() {
        adapter = new MovieAdapter();
        rvMovies.setAdapter(adapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        addScrollListener();
        addTextChangedListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
    }

    /**
     * Infinite scroll method:
     * If scroll reachs the penultimate visible item, ask for more results to the server.
     */
    private void addScrollListener() {
        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager =
                        (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.findLastVisibleItemPosition()
                        == layoutManager.getItemCount() - 2) {
                    loadMore();
                }
            }
        });
    }

    private void loadMore() {
        if (editTextSearch.getText() != null &&
                !editTextSearch.getText().toString().isEmpty()) {
            presenter.searchMoreMoviesByKeyword(editTextSearch.getText().toString());
        } else {
            presenter.loadMorePopularMovies();
        }
    }

    private void addTextChangedListener() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override public void afterTextChanged(Editable editable) {
                handler.removeCallbacks(workRunnable);
                workRunnable = () -> searchAfterTextChanges(editable.toString());
                handler.postDelayed(workRunnable, DELAY_MILLIS);
            }
        });
    }

    private void searchAfterTextChanges(String query) {
        adapter.clearList();
        // After every text change, we want to see the 1st page of the results
        presenter.resetPage();
        // If user deletes all the query, load popular movies again (as when the app is opened)
        if (!query.isEmpty()) {
            presenter.getMoviesByKeyWord(query);
        } else {
            presenter.getPopularMovies();
        }
    }

    @Override
    public void addMovies(List<Movie> list) {
        adapter.setMovieList(list);
    }

    @OnClick(R.id.btn_retry) public void clickRetry() {
        hideRetryButton();
        loadMore();
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

    @Override public void showRecyclerView() {
        rvMovies.setVisibility(View.VISIBLE);
    }

    @Override public void hideRecyclerView() {
        rvMovies.setVisibility(View.GONE);
    }

    @Override public void showNoMoreResultsErrorToast() {
        Toast.makeText(this, R.string.main_activity_error_no_more_results, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingErrorToast() {
        Toast.makeText(this, R.string.main_activity_error_loading, Toast.LENGTH_SHORT).show();
    }
}
