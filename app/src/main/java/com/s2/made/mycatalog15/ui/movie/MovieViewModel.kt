package com.s2.made.mycatalog15.ui.movie

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.s2.made.core.domain.usecase.CatalogUseCase

class MovieViewModel(catalogUseCase: CatalogUseCase) : ViewModel() {
    val moviePopular = LiveDataReactiveStreams.fromPublisher(catalogUseCase.getMoviesPopular())
}