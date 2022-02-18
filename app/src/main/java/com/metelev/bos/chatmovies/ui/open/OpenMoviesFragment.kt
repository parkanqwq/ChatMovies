package com.metelev.bos.chatmovies.ui.open

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.metelev.bos.chatmovies.R
import com.metelev.bos.chatmovies.domain.ActorEntity
import com.metelev.bos.chatmovies.domain.MovieEntity
import com.metelev.bos.chatmovies.rest.AppState
import com.metelev.bos.chatmovies.ui.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_open_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OpenMoviesFragment : BaseFragment(R.layout.fragment_open_movies) {

    private val viewModel: OpenMoviesViewModel by viewModel()

    override fun onResume() {
        super.onResume()

        initView()
    }

    private fun initView() {
        val movie = arguments?.getParcelable<MovieEntity>(BUNDLE_EXTRA)
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        recycler_view.adapter = adapterImages

        Picasso.get()
            .load(patch + movie?.backdrop_path)
            .into(image_movie)
        name_overview.text = movie?.overview.toString()
        ratingBar.rating = movie?.vote_average?.toFloat()?.div(2) ?: rating
        rating_num.text = ratingNum + movie?.vote_average.toString()
        name_movies.text = movie?.original_title.toString()
        movie?.id?.let { viewModel.getMoviesFilm(it) }
        initToolbar(movie)
    }

    private val adapterImages = AdapterActor()

    private fun initToolbar(movie: MovieEntity?) {
        val toolbar = activity?.findViewById<Toolbar>(R.id.tool_bar)
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity!!.setSupportActionBar(toolbar)
        appCompatActivity.title = movie?.original_title.toString()
        setHasOptionsMenu(true)
    }

    private fun getAdapterMyFriends(movieEntity: ArrayList<ActorEntity>) {
        adapterImages.setProfileInfo(movieEntity)
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.SuccessMovies -> {

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