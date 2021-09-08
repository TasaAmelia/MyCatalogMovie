package com.s2.made.core.datasource.remote.network

import com.s2.made.core.datasource.remote.response.ContentMovieResponse
import com.s2.made.core.datasource.remote.response.MovieResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("movie/popular")
    fun getMoviePopular(
            @Query("api_key") api_key: String,
            @Query("language") language: String,
            @Query("page") page: String,
    ): Flowable<ContentMovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
            @Path("movie_id") movie_id: String,
            @Query("api_key") api_key: String,
            @Query("language") language: String
    ): Flowable<MovieResponse>
}