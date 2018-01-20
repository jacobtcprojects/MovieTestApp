package com.challenge.jacobtcantera.movietestapp.di;

import com.challenge.jacobtcantera.movietestapp.view.MainActivity;

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