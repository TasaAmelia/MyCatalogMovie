package com.s2.made.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        val id: Int = 0,
        val title: String? = "title",
        val poster_path: String? = "poster_path",
        val overview: String? = "overview",
        val release_date: String? = "release_date",
        val vote_average: Double? = 0.0,
        val popularity: Double? = 0.0,
        val genre: String? = "genre",
        var favorite: Boolean = false,
        val category: String? = "category",
        val length: Int? = 0
) : Parcelable