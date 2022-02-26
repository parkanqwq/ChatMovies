package com.metelev.bos.chatmovies.ui.movies

import androidx.lifecycle.*
import com.metelev.bos.chatmovies.repository.Repository
import com.metelev.bos.chatmovies.rest.AppState
import kotlinx.coroutines.*

class MoviesViewModel(private val repository: Repository)
    : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    fun getLiveData() = liveDataToObserve

    fun getMoviesFilm(query: String) = getDataFromMovieSource(query)

    private fun getDataFromMovieSource(query: String) {
        liveDataToObserve.value = AppState.Loading
        launch {
            val localStorageJob = async(Dispatchers.IO) {
                repository.getMoviesFromServer(query)
            }
            if (localStorageJob.await()[0].id == null) {
                liveDataToObserve.value = AppState.Error("error: " + localStorageJob.await()[0].poster_path)
            } else {
                liveDataToObserve.value = AppState.SuccessMovies(localStorageJob.await())
            }
        }
    }
}