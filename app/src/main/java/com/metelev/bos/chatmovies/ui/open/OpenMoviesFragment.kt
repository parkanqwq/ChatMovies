package com.metelev.bos.chatmovies.ui.open

import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.metelev.bos.chatmovies.R
import com.metelev.bos.chatmovies.domain.ActorEntity
import com.metelev.bos.chatmovies.domain.MovieEntity
import com.metelev.bos.chatmovies.domain.MoviesDbEntity
import com.metelev.bos.chatmovies.repository.MoviesRepo
import com.metelev.bos.chatmovies.rest.AppState
import com.metelev.bos.chatmovies.ui.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_open_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject

class OpenMoviesFragment : BaseFragment(R.layout.fragment_open_movies) {

    private val moviesRepo: MoviesRepo by inject(MoviesRepo::class.java)
    private val viewModel: OpenMoviesViewModel by viewModel()
    private var buttonHistory = false

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        val movie = arguments?.getParcelable<MovieEntity>(BUNDLE_EXTRA)
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getButton(movie)
        viewModel.getRecommendations(movie?.id)

        recycler_view.adapter = adapterImages
        recommendations_recycler_view.adapter = adapterMoviesRec

        Picasso.get()
            .load(patch + movie?.backdrop_path)
            .into(image_movie)
        name_overview.text = movie?.overview.toString()
        ratingBar.rating = movie?.vote_average?.toFloat()?.div(2) ?: rating
        rating_num.text = ratingNum + movie?.vote_average.toString()
        name_movies.text = movie?.original_title.toString()
        movie?.id?.let { viewModel.getMoviesFilm(it) }
        initToolbar(movie)

        save_movies.setOnClickListener {
            buttonHistory = if (buttonHistory) {
                viewModel.getDeleteMovieDb(convertMovie(movie))
                save_movies.setBackgroundResource(R.drawable.ic_bookmark)
                false
            } else {
                viewModel.getSaveMovie(moviesRepo, convertMovie(movie))
                save_movies.setBackgroundResource(R.drawable.ic_bookmark_add)
                true
            }
        }
    }

    private fun convertMovie(movie: MovieEntity?): MoviesDbEntity {
        return MoviesDbEntity(
            movie?.id.toString(),
            movie?.poster_path.toString(),
            movie?.original_title.toString(),
            movie?.vote_average.toString(),
            movie?.release_date.toString(),
            movie?.original_language.toString(),
            movie?.runtime.toString(),
            movie?.overview.toString(),
            movie?.backdrop_path.toString(),
            movie?.adult.toString()
        )
    }

    private fun initToolbar(movie: MovieEntity?) {
        val toolbar = activity?.findViewById(R.id.tool_bar) as Toolbar
        toolbar.title = movie?.original_title.toString()
        setHasOptionsMenu(true)
    }

    private val adapterImages = AdapterActor()
    private fun getAdapterMyFriends(actorEntity: ArrayList<ActorEntity>) {
        adapterImages.setProfileInfo(actorEntity)
    }

    private val onObjectListener = object : OnItemViewClickListener {
        override fun onItemViewClick(movieEntity: MovieEntity) {
            val bundle = Bundle()
            bundle.putParcelable(OpenMoviesFragment.BUNDLE_EXTRA, movieEntity)
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.add(R.id.activity_main, OpenMoviesFragment.newInstance(bundle))
                ?.addToBackStack("")
                ?.commit()
        }
    }

    private fun getAdapterRec(movieEntity: ArrayList<MovieEntity>) {
        adapterMoviesRec.setRecList(movieEntity)
    }

    private val adapterMoviesRec = AdapterMoviesRec(onObjectListener)

    interface OnItemViewClickListener {
        fun onItemViewClick(movieEntity: MovieEntity)
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.SuccessMovies -> {
                Log.d("1234", data.data.toString())
                getAdapterRec(data.data)
            }
            is AppState.HaveMovie -> {
                buttonHistory = data.data
                if (data.data)
                    save_movies.setBackgroundResource(R.drawable.ic_bookmark_add)
                else
                    save_movies.setBackgroundResource(R.drawable.ic_bookmark)
            }
            is AppState.SuccessActors -> {
                getAdapterMyFriends(data.data)
            }
            is AppState.Loading -> {
                //showLoading()
            }
            is AppState.Error -> {
                //showError(data.error.message)
            }
        }
    }

    companion object {
        const val ratingNum = "rating: "
        const val rating = 3f
        const val BUNDLE_EXTRA = "movies_bundle"
        const val patch = "https://image.tmdb.org/t/p/original"

        @JvmStatic
        fun newInstance(bundle: Bundle): OpenMoviesFragment {
            val fragment = OpenMoviesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}