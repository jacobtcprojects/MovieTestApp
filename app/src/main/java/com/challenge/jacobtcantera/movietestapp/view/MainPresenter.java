package com.challenge.jacobtcantera.movietestapp.view;

import android.util.Log;

import com.challenge.jacobtcantera.movietestapp.rest.RestServiceManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jacob on 20/01/2018.
 */

public class MainPresenter {
    public interface View {   }

    private View view;

    private RestServiceManager restServiceManager;
    @Inject
    public MainPresenter(RestServiceManager restServiceManager) {
        this.restServiceManager = restServiceManager;
    }

    public void initView(View view) {
        this.view = view;
    }

    public void destroyView(){
        this.view = null;
    }

    public void doCall() {
        restServiceManager
                .getPopularMoviesCall(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> log(movieResponse.getResults().get(1).getTitle()),
                        Throwable::printStackTrace);

    }

    private void log(String title) {
        Log.d("MainPresenter", title);
    }
}