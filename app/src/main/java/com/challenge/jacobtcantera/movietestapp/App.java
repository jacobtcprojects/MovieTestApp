package com.challenge.jacobtcantera.movietestapp;

import android.app.Application;

import com.challenge.jacobtcantera.movietestapp.di.DaggerRestComponent;
import com.challenge.jacobtcantera.movietestapp.di.RestComponent;
import com.challenge.jacobtcantera.movietestapp.di.RestModule;

/**
 * Created by jacob on 20/01/2018.
 */

public class App extends Application {
    RestComponent restComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        restComponent = DaggerRestComponent.builder()
                .restModule(new RestModule())
                .build();
    }

    public RestComponent getRestComponent() {
        return restComponent;
    }
}

