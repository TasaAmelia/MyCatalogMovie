package com.s2.made.core.domain.usecase

import com.s2.made.core.datasource.Resource
import com.s2.made.core.domain.model.Movie
import io.reactivex.Flowable

interface CatalogUseCase {
    fun getMoviesPopular(): Flowable<Resource<List<Movie>>>
    fun getDetailMovie(movieId: String): Flowable<Resource<Movie>>
    fun getFavoriteMovie(): Flowable<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}