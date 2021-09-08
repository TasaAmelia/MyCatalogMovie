package com.s2.made.favorite

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.s2.made.core.domain.usecase.CatalogUseCase

class FavoriteViewModel(catalogUseCase: CatalogUseCase) : ViewModel() {
    val favoriteMovie = LiveDataReactiveStreams.fromPublisher(catalogUseCase.getFavoriteMovie())
}