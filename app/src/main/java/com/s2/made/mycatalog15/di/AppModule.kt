package com.s2.made.mycatalog15.di

import com.s2.made.core.domain.usecase.CatalogInteractor
import com.s2.made.core.domain.usecase.CatalogUseCase
import com.s2.made.mycatalog15.ui.detail.DetailMovieViewModel
import com.s2.made.mycatalog15.ui.movie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object  AppModule {
    val useCaseModule = module {
        factory<CatalogUseCase> { CatalogInteractor(get()) }
    }

    val viewModelModule = module {
        viewModel { MovieViewModel(get()) }
        viewModel { DetailMovieViewModel(get()) }
    }
}