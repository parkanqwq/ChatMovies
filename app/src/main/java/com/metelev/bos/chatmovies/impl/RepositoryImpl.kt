package com.metelev.bos.chatmovies.impl

import com.metelev.bos.chatmovies.domain.MovieEntity
import com.metelev.bos.chatmovies.repository.Repository
import com.metelev.bos.chatmovies.rest.MoviesApi
import org.koin.java.KoinJavaComponent.inject

class RepositoryImpl : Repository {

    private val moviesApi: MoviesApi by inject(MoviesApi::class.java)

    override fun getMoviesFromServer(query: String): ArrayList<MovieEntity> {
        val dto = moviesApi.getMoviesAsync(api_key, false,query).execute().body()
        val moviesList = arrayListOf<MovieEntity>()

        if (dto?.results != null)
        for (result in dto.results) {
            if (result.backdrop_path != null)
            moviesList.add(result)
        }
        return moviesList
    }

    companion object {
        private const val api_key = "a7fe7b51456e94640008bbb9e3a50dd5"
    }
}