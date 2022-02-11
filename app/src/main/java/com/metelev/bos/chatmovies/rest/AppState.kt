package com.metelev.bos.chatmovies.rest

import com.metelev.bos.chatmovies.domain.MovieEntity


sealed class AppState {
    data class Success(val data: ArrayList<MovieEntity>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
