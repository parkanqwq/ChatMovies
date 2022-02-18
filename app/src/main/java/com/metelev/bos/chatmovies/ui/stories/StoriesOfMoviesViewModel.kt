package com.metelev.bos.chatmovies.ui.stories

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.metelev.bos.chatmovies.repository.MoviesRepo
import com.metelev.bos.chatmovies.rest.AppState
import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent.inject

class StoriesOfMoviesViewModel
    : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {

    private val moviesRepo: MoviesRepo by inject(MoviesRepo::class.java)
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    fun getLiveData() = liveDataToObserve

    fun getNewMovies() = getDataFromMovieSource()

    private fun getDataFromMovieSource() {
        liveDataToObserve.value = AppState.Loading
        launch {
            val localStorageJob = async(Dispatchers.IO) {
                moviesRepo.image
            }
            liveDataToObserve.value = AppState.SuccessMoviesDb(localStorageJob.await())
        }
    }
}