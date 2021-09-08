package com.s2.made.core.domain.usecase

import com.s2.made.core.domain.model.Movie
import com.s2.made.core.domain.repository.ICatalogRepository

class CatalogInteractor(private val catalogRepository: ICatalogRepository) : CatalogUseCase {
    override fun getMoviesPopular() = catalogRepository.getMoviesPopular()
    override fun getDetailMovie(movieId: String) = catalogRepository.getDetailMovie(movieId)
    override fun getFavoriteMovie() = catalogRepository.getFavoriteMovie()
    override fun setFavoriteMovie(movie: Movie, state: Boolean) = catalogRepository.setFavoriteMovie(movie, state)
}