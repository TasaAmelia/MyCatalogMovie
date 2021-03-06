package com.s2.made.favorite

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FavoriteModule {
    val favoriteModule = module {
        viewModel { FavoriteViewModel(get()) }
    }
}