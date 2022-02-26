package com.metelev.bos.chatmovies.ui.stories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.metelev.bos.chatmovies.R
import com.metelev.bos.chatmovies.domain.MovieEntity
import com.metelev.bos.chatmovies.domain.MoviesDbEntity
import com.metelev.bos.chatmovies.rest.AppState
import com.metelev.bos.chatmovies.ui.base.BaseFragment
import com.metelev.bos.chatmovies.ui.latest.AdapterNewMovies
import com.metelev.bos.chatmovies.ui.latest.NewMoviesViewModel
import com.metelev.bos.chatmovies.ui.open.OpenMoviesFragment
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class StoriesOfMoviesFragment : BaseFragment(R.layout.fragment_stories_of_movies) {

    private val viewModel: StoriesOfMoviesViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getNewMovies()
    }

    override fun onResume() {
        super.onResume()

        recycler_view.adapter = adapterImages
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getNewMovies()
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.SuccessMoviesDb -> {
                getAdapterMyFriends(data.data)
                progress_circular.visibility = View.GONE
            }
            is AppState.Loading -> {
                progress_circular.visibility = View.VISIBLE
            }
            is AppState.Error -> {

                progress_circular.visibility = View.GONE
            }
        }
    }

    private fun convertMovie(movie: MoviesDbEntity?): MovieEntity {
        return MovieEntity(
            movie?.id?.toInt(),
            movie?.poster_path.toString(),
            movie?.original_title.toString(),
            movie?.vote_average?.toDouble(),
            movie?.release_date.toString(),
            movie?.original_language.toString(),
            null,//movie?.runtime?.toInt(),
            movie?.overview.toString(),
            movie?.backdrop_path.toString(),
            movie?.adult?.toBoolean()
        )
    }

    private val onObjectListener = object : OnItemViewClickListener {
        override fun onItemViewClick(movieEntity: MoviesDbEntity) {
            val bundle = Bundle()
            bundle.putParcelable(OpenMoviesFragment.BUNDLE_EXTRA, convertMovie(movieEntity))
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.add(R.id.activity_main, OpenMoviesFragment.newInstance(bundle))
                ?.addToBackStack("")
                ?.commitAllowingStateLoss()
        }
    }

    private fun getAdapterMyFriends(movieEntity: List<MoviesDbEntity>) {
        adapterImages.setProfileInfo(movieEntity.asReversed())
    }

    private val adapterImages = AdapterStories(onObjectListener)

    interface OnItemViewClickListener {
        fun onItemViewClick(movieEntity: MoviesDbEntity)
    }
}