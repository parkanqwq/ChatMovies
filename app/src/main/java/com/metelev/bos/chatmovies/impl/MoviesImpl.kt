package com.metelev.bos.chatmovies.impl

import com.metelev.bos.chatmovies.domain.MoviesDbEntity
import com.metelev.bos.chatmovies.repository.MoviesRepo
import com.metelev.bos.chatmovies.rest.MoviesDao

class MoviesImpl(private val moviesDao: MoviesDao) : MoviesRepo {
    override fun movies(): List<MoviesDbEntity>{
        return  moviesDao.getMoviesDb()
    }

    override fun put(movie: MoviesDbEntity) {
        moviesDao.put(movie)
    }

    override fun clear() {
        moviesDao.clear()
    }

    override fun delete(movie: MoviesDbEntity) {
        moviesDao.delete(movie)
    }
}