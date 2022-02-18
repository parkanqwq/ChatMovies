package com.metelev.bos.chatmovies.repository

import com.metelev.bos.chatmovies.domain.ActorEntity
import com.metelev.bos.chatmovies.domain.MovieEntity

interface Repository {
    fun getMoviesFromServer(query: String): ArrayList<MovieEntity>
    fun getMoviesActorServer(id: Int): ArrayList<ActorEntity>
    fun getMoviesPopularServer(): ArrayList<MovieEntity>
}