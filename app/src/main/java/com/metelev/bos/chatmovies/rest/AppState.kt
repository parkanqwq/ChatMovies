package com.metelev.bos.chatmovies.rest

import com.metelev.bos.chatmovies.domain.ActorEntity
import com.metelev.bos.chatmovies.domain.MovieEntity


sealed class AppState {
    data class SuccessMovies(val data: ArrayList<MovieEntity>) : AppState()
    data class SuccessActors(val data: ArrayList<ActorEntity>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
