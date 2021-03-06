package com.challenge.jacobtcantera.movietestapp.rest;

import android.support.annotation.NonNull;

import com.challenge.jacobtcantera.movietestapp.rest.response.MovieResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by jacob on 20/01/2018.
 */

public class RestServiceManagerImpl implements RestServiceManager {
    private static final String APP_KEY ="93aea0c77bc168d8bbce3918cefefa45";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private final MovieApiService movieApiService;

    public RestServiceManagerImpl() {
        OkHttpClient movieApiServiceOkHttpClient = createOkHttpClient();
        movieApiService = RestUtils.createService(
                BASE_URL,
                MovieApiService.class,
                movieApiServiceOkHttpClient);
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(logging);

        okHttpClientBuilder.connectTimeout(30,
                TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(30,
                TimeUnit.SECONDS);

        return okHttpClientBuilder.build();
    }

    @Override
    public MovieApiService getMovieApiService() {
        return movieApiService;
    }

    @Override
    public String getApiKey() {
        return APP_KEY;
    }
}
