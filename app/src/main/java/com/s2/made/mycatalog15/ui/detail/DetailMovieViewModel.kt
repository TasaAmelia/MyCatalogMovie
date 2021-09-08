package com.s2.made.mycatalog15.ui.detail

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.s2.made.core.domain.model.Movie
import com.s2.made.core.domain.usecase.CatalogUseCase

class DetailMovieViewModel(private val catalogUseCase: CatalogUseCase) : ViewModel() {
    fun getDetailMovie(id: String, state: Boolean, category: String) = LiveDataReactiveStreams.fromPublisher(catalogUseCase.getDetailMovie(id))
    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) = catalogUseCase.setFavoriteMovie(movie, newStatus)
}