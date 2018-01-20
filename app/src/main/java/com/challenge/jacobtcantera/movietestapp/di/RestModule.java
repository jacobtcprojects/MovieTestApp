package com.challenge.jacobtcantera.movietestapp.di;

import com.challenge.jacobtcantera.movietestapp.rest.RestServiceManager;
import com.challenge.jacobtcantera.movietestapp.rest.RestServiceManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jacob on 20/01/2018.
 */
@Module
public class RestModule {

    @Provides
    @Singleton
    RestServiceManager provideRestServiceManager() {
        return new RestServiceManagerImpl();
    }
}
