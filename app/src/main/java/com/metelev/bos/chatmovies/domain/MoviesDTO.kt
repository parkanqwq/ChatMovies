package com.metelev.bos.chatmovies.domain

import androidx.collection.ArraySet

data class MoviesDTO(
    val results: ArraySet<MovieEntity>
)
