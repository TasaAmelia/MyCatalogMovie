package com.s2.made.core.datasource.remote

import android.annotation.SuppressLint
import com.s2.made.core.datasource.remote.network.ApiResponse
import com.s2.made.core.datasource.remote.network.ApiServices
import com.s2.made.core.datasource.remote.response.MovieResponse
import com.s2.made.core.BuildConfig
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

@Suppress("UNCHECKED_CAST")
@SuppressLint("CheckResult")
class RemoteDataSource(private val apiServices: ApiServices) {

    fun getMoviesPopular(): Flowable<ApiResponse<List<MovieResponse>>> {
        val resultMovie = PublishSubject.create<ApiResponse<List<MovieResponse>>>()

        val client = apiServices.getMoviePopular(API_KEY, QUERY_LANGUAGE, QUERY_PAGE)

        client
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe({ response ->
                    val dataArray = response.result
                    resultMovie.onNext((if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty) as ApiResponse<List<MovieResponse>>)
                }, { error ->
                    resultMovie.onNext(ApiResponse.Error(error.message.toString()))
                })

        return resultMovie.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getDetailMovie(movieId: String): Flowable<ApiResponse<MovieResponse>> {
        val resultDetailMovie = PublishSubject.create<ApiResponse<MovieResponse>>()

        val client = apiServices.getMovieDetail(movieId, API_KEY, QUERY_LANGUAGE)

        client
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe({ response ->
                    resultDetailMovie.onNext(if (response.id != null) ApiResponse.Success(response) else ApiResponse.Empty)
                }, { error ->
                    resultDetailMovie.onNext(ApiResponse.Error(error.message.toString()))
                })

        return resultDetailMovie.toFlowable(BackpressureStrategy.BUFFER)
    }

    companion object {
        private const val API_KEY = BuildConfig.MOVIE_TOKEN
        private const val QUERY_LANGUAGE = "en-US"
        private const val QUERY_PAGE = "1"
    }

}