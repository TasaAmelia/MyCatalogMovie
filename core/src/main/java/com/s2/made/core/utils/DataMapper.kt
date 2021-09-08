package com.s2.made.core.utils

import com.s2.made.core.datasource.local.entity.MovieEntity
import com.s2.made.core.datasource.remote.response.MovieResponse
import com.s2.made.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(
            input: List<MovieResponse>
    ): List<MovieEntity> {
        val catalogList = ArrayList<MovieEntity>()
        var genre = ""
        input.map {
            it.genres?.forEach { gen ->
                genre += "${gen?.name}, "
            }

            val catalog = MovieEntity(
                    id = it.id ?: 0,
                    title = it.title!!,
                    poster_path = it.posterPath!!,
                    overview = it.overview!!,
                    release_date = it.releaseDate!!,
                    vote_average = it.voteAverage!!,
                    popularity = it.popularity!!,
                    genre = genre,
                    favorite = false
            )

            catalogList.add(catalog)
        }
        return catalogList
    }

    fun mapResponseToEntity(
            input: MovieResponse,
    ): MovieEntity {
        var genre = ""
        input.genres?.forEach { gen ->
            genre += "${gen?.name}, "
        }
        return MovieEntity(
                id = input.id ?: 0,
                title = input.title!!,
                poster_path = input.posterPath!!,
                overview = input.overview!!,
                release_date = input.releaseDate!!,
                vote_average = input.voteAverage!!,
                popularity = input.popularity!!,
                genre = genre,
                favorite = false
        )
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
            input.map {
                Movie(
                        id = it.id,
                        title = it.title,
                        poster_path = it.poster_path,
                        overview = it.overview,
                        release_date = it.release_date,
                        vote_average = it.vote_average,
                        popularity = it.popularity,
                        genre = it.genre,
                        favorite = it.favorite
                )
            }

    fun mapEntityToDomain(input: MovieEntity): Movie =
            Movie(
                    id = input.id,
                    title = input.title,
                    poster_path = input.poster_path,
                    overview = input.overview,
                    release_date = input.release_date,
                    vote_average = input.vote_average,
                    popularity = input.popularity,
                    genre = input.genre,
                    favorite = input.favorite
            )

    fun mapDomainToEntity(input: Movie) = MovieEntity(
            id = input.id,
            title = input.title!!,
            poster_path = input.poster_path!!,
            overview = input.overview!!,
            release_date = input.release_date!!,
            vote_average = input.vote_average!!,
            popularity = input.popularity!!,
            genre = input.genre!!,
            favorite = input.favorite

    )


}