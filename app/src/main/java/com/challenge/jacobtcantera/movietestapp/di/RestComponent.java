package com.challenge.jacobtcantera.movietestapp.di;


import com.challenge.jacobtcantera.movietestapp.presentation.view.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jacob on 20/01/2018.
 */

@Singleton
@Component(modules = RestModule.class)
public interface RestComponent {
    void inject(MainActivity target);

}