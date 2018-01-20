package com.challenge.jacobtcantera.movietestapp.rest;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jacob on 20/01/2018.
 */

public interface MovieApiService {

    @GET("movie/popular")
    Single<MovieResponse> getPopularMovies(@Query("api_key") String appKey,
                                           @Query("page") int page);
    @GET("search/movie")
    Single<MovieResponse> searchByKeyword(@Query("api_key") String appKey,
                                          @Query("query") String query,
                                          @Query("page") int page);
}
