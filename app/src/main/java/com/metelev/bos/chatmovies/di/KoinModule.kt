package com.metelev.bos.chatmovies.di

import androidx.room.Room
import com.metelev.bos.chatmovies.impl.MoviesImpl
import com.metelev.bos.chatmovies.repository.Repository
import com.metelev.bos.chatmovies.impl.RepositoryImpl
import com.metelev.bos.chatmovies.repository.MoviesRepo
import com.metelev.bos.chatmovies.rest.ApiUtils
import com.metelev.bos.chatmovies.rest.MoviesApi
import com.metelev.bos.chatmovies.rest.MoviesDb
import com.metelev.bos.chatmovies.ui.latest.NewMoviesViewModel
import com.metelev.bos.chatmovies.ui.movies.MoviesViewModel
import com.metelev.bos.chatmovies.ui.open.OpenMoviesViewModel
import com.metelev.bos.chatmovies.ui.stories.StoriesOfMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { OpenMoviesViewModel(get()) }
    viewModel { NewMoviesViewModel(get()) }
    viewModel { StoriesOfMoviesViewModel() }
    single<Repository> { RepositoryImpl() }
    val dbPatchRepositories = "movies.db"
    single { Room.databaseBuilder(get(), MoviesDb::class.java, dbPatchRepositories).build() }
    single { get<MoviesDb>().moviesDao() }
    single<MoviesRepo> { MoviesImpl(get()) }
}

val retrofitModule = module {
    val baseUrlMainPart = "https://api.themoviedb.org/"
    single<MoviesApi> {
        Retrofit.Builder()
            .baseUrl(baseUrlMainPart)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build().create(MoviesApi::class.java)
    }
}