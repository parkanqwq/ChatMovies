package com.metelev.bos.chatmovies.rest

import com.metelev.bos.chatmovies.domain.MoviesDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("3/search/movie")
    fun getMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("query") query: String
    ) : Call<MoviesDTO>
}