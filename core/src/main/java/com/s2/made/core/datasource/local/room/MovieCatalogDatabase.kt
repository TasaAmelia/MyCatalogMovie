package com.s2.made.core.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.s2.made.core.datasource.local.entity.MovieEntity


@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieCatalogDatabase : RoomDatabase() {
    abstract fun movieCatalogDao(): MovieCatalogDao
}