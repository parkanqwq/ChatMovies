package com.metelev.bos.chatmovies.rest

import androidx.room.Database
import androidx.room.RoomDatabase
import com.metelev.bos.chatmovies.domain.MoviesDbEntity


@Database(
    entities = [MoviesDbEntity::class],
    version = 1
)

abstract class MoviesDb : RoomDatabase() {
    abstract fun moviesDao() : MoviesDao
}