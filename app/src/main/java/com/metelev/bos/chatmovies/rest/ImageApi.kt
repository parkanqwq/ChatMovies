package com.metelev.bos.chatmovies.rest

import com.metelev.bos.chatmovies.domain.ActorDTO
import com.metelev.bos.chatmovies.domain.MoviesDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("3/search/movie")
    fun getMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("query") query: String
    ) : Call<MoviesDTO>

    @GET("3/movie/{movie_id}/credits")
    fun getMoviesOpenAsync(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ) : Call<ActorDTO>

    @GET("3/movie/popular")
    fun getMoviesPopularAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ) : Call<MoviesDTO>
}