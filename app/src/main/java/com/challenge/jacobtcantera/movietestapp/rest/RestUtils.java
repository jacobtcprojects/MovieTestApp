package com.challenge.jacobtcantera.movietestapp.rest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jacob on 20/01/2018.
 */

public class RestUtils {

    public static <S> S createService(String restApiBaseUrl,
                                      Class<S> serviceClass,
                                      OkHttpClient httpClient) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(restApiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient);

        Retrofit adapter = builder.build();
        return adapter.create(serviceClass);
    }
}
