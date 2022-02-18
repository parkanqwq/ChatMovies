package com.metelev.bos.chatmovies.repository

import com.metelev.bos.chatmovies.domain.MoviesDbEntity

interface MoviesRepo {
    val image: List<MoviesDbEntity>
    fun put(movie: MoviesDbEntity)
    fun clear()
    fun delete(movie: MoviesDbEntity)
}