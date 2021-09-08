package com.s2.made.core.datasource.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_entities")
data class MovieEntity(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "id")
        var id: Int,
        @ColumnInfo(name = "title")
        var title: String,
        @ColumnInfo(name = "poster_path")
        var poster_path: String,
        @ColumnInfo(name = "overview")
        var overview: String,
        @ColumnInfo(name = "release_date")
        var release_date: String,
        @ColumnInfo(name = "vote_average")
        var vote_average: Double,
        @ColumnInfo(name = "popularity")
        var popularity: Double,
        @ColumnInfo(name = "genre")
        var genre: String,
        @ColumnInfo(name = "favorite")
        var favorite: Boolean = false,
        @ColumnInfo(name = "category")
        var category: String? = "category",
        @ColumnInfo(name = "length")
        var length: Int? = 0
)