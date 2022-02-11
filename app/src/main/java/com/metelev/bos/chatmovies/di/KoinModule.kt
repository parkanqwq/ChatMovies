package com.metelev.bos.chatmovies.di

import com.metelev.bos.chatmovies.repository.Repository
import com.metelev.bos.chatmovies.impl.RepositoryImpl
import com.metelev.bos.chatmovies.rest.ApiUtils
import com.metelev.bos.chatmovies.rest.MoviesApi
import com.metelev.bos.chatmovies.ui.movies.MoviesViewModel
import com.metelev.bos.chatmovies.ui.open.OpenMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { OpenMoviesViewModel(get()) }
    single<Repository> { RepositoryImpl() }
}

val retrofitModule = module {
    val baseUrlMainPart = "https://api.themoviedb.org/"
    factory<MoviesApi> {
        Retrofit.Builder()
            .baseUrl(baseUrlMainPart)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build().create(MoviesApi::class.java)
    }
}