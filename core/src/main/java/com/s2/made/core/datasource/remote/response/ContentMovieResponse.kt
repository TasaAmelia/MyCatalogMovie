package com.s2.made.core.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class ContentMovieResponse(
        @field:SerializedName("results")
        @JvmField val result: List<MovieResponse?>? = null
)