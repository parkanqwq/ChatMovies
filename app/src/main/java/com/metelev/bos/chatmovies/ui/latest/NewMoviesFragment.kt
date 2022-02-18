package com.metelev.bos.chatmovies.ui.latest

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.metelev.bos.chatmovies.R
import com.metelev.bos.chatmovies.domain.MovieEntity
import com.metelev.bos.chatmovies.rest.AppState
import com.metelev.bos.chatmovies.ui.base.BaseFragment
import com.metelev.bos.chatmovies.ui.movies.AdapterMovies
import com.metelev.bos.chatmovies.ui.open.OpenMoviesFragment
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewMoviesFragment : BaseFragment(R.layout.fragment_new_movies) {

    private val viewModel: NewMoviesViewModel by viewModel()

    override fun onResume() {
        super.onResume()

        recycler_view.adapter = adapterImages
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getNewMovies()
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.SuccessMovies -> {
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

    private fun getAdapterMyFriends(movieEntity: ArrayList<MovieEntity>) {
        adapterImages.setProfileInfo(movieEntity)
    }

    private val adapterImages = AdapterNewMovies(onObjectListener)

    interface OnItemViewClickListener {
        fun onItemViewClick(movieEntity: MovieEntity)
    }
}