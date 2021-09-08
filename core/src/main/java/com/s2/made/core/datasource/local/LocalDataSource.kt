package com.s2.made.core.datasource.local


import com.s2.made.core.datasource.local.entity.MovieEntity
import com.s2.made.core.datasource.local.room.MovieCatalogDao
import io.reactivex.Flowable


class LocalDataSource(private val mMovieCatalogDao: MovieCatalogDao) {

    fun getMovies(): Flowable<List<MovieEntity>> = mMovieCatalogDao.getMovies()

    fun getFavoriteMovie(): Flowable<List<MovieEntity>> = mMovieCatalogDao.getFavoriteMovie()

    fun getMovieById(movieId: String): Flowable<MovieEntity> =
            mMovieCatalogDao.getMovieById(movieId)

    fun insertMovies(movies: List<MovieEntity>) = mMovieCatalogDao.insertMovies(movies)

    fun updateMovie(movie: MovieEntity) = mMovieCatalogDao.updateMovie(movie)

    fun setFavoriteMovie(movies: MovieEntity, newState: Boolean) {
        movies.favorite = newState
        mMovieCatalogDao.updateFavoriteMovie(movies)
    }


}