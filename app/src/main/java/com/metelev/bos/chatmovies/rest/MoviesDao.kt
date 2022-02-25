package com.metelev.bos.chatmovies.rest

import androidx.room.*
import com.metelev.bos.chatmovies.domain.MoviesDbEntity

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getMoviesDb() : List<MoviesDbEntity>

    @Query("DELETE FROM movies")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(user: MoviesDbEntity)

    @Delete
    fun delete(user: MoviesDbEntity)
}