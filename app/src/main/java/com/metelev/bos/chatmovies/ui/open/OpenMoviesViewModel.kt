package com.metelev.bos.chatmovies.ui.open

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.metelev.bos.chatmovies.repository.Repository
import com.metelev.bos.chatmovies.rest.AppState
import kotlinx.coroutines.*

class OpenMoviesViewModel(private val repository: Repository)
    : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    fun getLiveData() = liveDataToObserve

    fun getMoviesFilm(id: Int) = getDataFromMovieSource(id)

    private fun getDataFromMovieSource(id: Int) {
        liveDataToObserve.value = AppState.Loading
        launch {
            val localStorageJob = async(Dispatchers.IO) {
                repository.getMoviesActorServer(id)
            }
            liveDataToObserve.value = AppState.SuccessActors(localStorageJob.await())
        }
    }
}