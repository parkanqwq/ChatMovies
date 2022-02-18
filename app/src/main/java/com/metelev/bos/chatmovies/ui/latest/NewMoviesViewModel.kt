package com.metelev.bos.chatmovies.ui.latest

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.metelev.bos.chatmovies.repository.Repository
import com.metelev.bos.chatmovies.rest.AppState
import kotlinx.coroutines.*

class NewMoviesViewModel(private val repository: Repository)
    : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    fun getLiveData() = liveDataToObserve

    fun getNewMovies() = getDataFromMovieSource()

    private fun getDataFromMovieSource() {
        liveDataToObserve.value = AppState.Loading
        launch {
            val localStorageJob = async(Dispatchers.IO) {
                repository.getMoviesPopularServer()
            }
            liveDataToObserve.value = AppState.SuccessMovies(localStorageJob.await())
        }
    }
}