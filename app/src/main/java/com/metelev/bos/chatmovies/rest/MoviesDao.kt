package com.metelev.bos.chatmovies.rest

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.metelev.bos.chatmovies.domain.MoviesDbEntity
import java.util.*

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getMoviesDb() : List<MoviesDbEntity>

    @Query("DELETE FROM movies")
    fun clear()

    @Insert
    fun put(user: MoviesDbEntity)

    @Delete
    fun delete(user: MoviesDbEntity)
}