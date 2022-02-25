package com.metelev.bos.chatmovies.ui.open

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.metelev.bos.chatmovies.domain.MovieEntity
import com.metelev.bos.chatmovies.domain.MoviesDbEntity
import com.metelev.bos.chatmovies.repository.MoviesRepo
import com.metelev.bos.chatmovies.repository.Repository
import com.metelev.bos.chatmovies.rest.AppState
import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent

class OpenMoviesViewModel(private val repository: Repository)
    : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {

    private val moviesRepo: MoviesRepo by KoinJavaComponent.inject(MoviesRepo::class.java)
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    fun getLiveData() = liveDataToObserve

    fun getMoviesFilm(id: Int) = getDataFromMovieSource(id)
    fun getSaveMovie(moviesRepo: MoviesRepo, movie: MoviesDbEntity) = getDataSaveMovie(moviesRepo, movie)
    fun getButton(movieDb: MovieEntity?) = getButtonChange(movieDb)
    fun getDeleteMovieDb(movie: MoviesDbEntity) = getDeleteMovie(movie)

    private fun getDeleteMovie(movie: MoviesDbEntity) {
        liveDataToObserve.value = AppState.Loading
        launch {
            val localStorageJob = async(Dispatchers.IO) {
                moviesRepo.delete(movie)
            }
        }
    }

    private fun getDataFromMovieSource(id: Int) {
        liveDataToObserve.value = AppState.Loading
        launch {
            val localStorageJob = async(Dispatchers.IO) {
                repository.getMoviesActorServer(id)
            }
            liveDataToObserve.value = AppState.SuccessActors(localStorageJob.await())
        }
    }

    private fun getDataSaveMovie(moviesRepo: MoviesRepo, movie: MoviesDbEntity) {
        liveDataToObserve.value = AppState.Loading
        launch {
            val localStorageJob = async(Dispatchers.IO) {
                moviesRepo.put(movie)
            }
        }
    }

    private fun getButtonChange(movieDb: MovieEntity?) {
        launch {
            val localStorageJob = async(Dispatchers.IO) {
                val movies = moviesRepo.movies()
                var haveSaveMovie = false
                for (result in movies) {
                    if (result.id == movieDb?.id.toString())
                        haveSaveMovie = true
                }
                haveSaveMovie
            }
            liveDataToObserve.value = AppState.HaveMovie(localStorageJob.await())
        }
    }
}