package com.s2.made.core.datasource.local.room

import androidx.room.*
import com.s2.made.core.datasource.local.entity.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MovieCatalogDao {
    @Query("SELECT * FROM movie_entities")
    fun getMovies(): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movie_entities where favorite = 1")
    fun getFavoriteMovie(): Flowable<List<MovieEntity>>

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE id = :id")
    fun getMovieById(id: String): Flowable<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>): Completable

    @Update
    fun updateMovie(movie: MovieEntity): Completable

    @Update
    fun updateFavoriteMovie(movie: MovieEntity)
}