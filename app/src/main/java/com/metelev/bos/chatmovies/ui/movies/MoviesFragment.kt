package com.metelev.bos.chatmovies.ui.movies

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
import com.metelev.bos.chatmovies.ui.open.OpenMoviesFragment
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoviesFragment : BaseFragment(R.layout.fragment_movies) {

    private val viewModel: MoviesViewModel by viewModel()

    override fun onResume() {
        super.onResume()

        initToolbar()
        recycler_view.adapter = adapterImages
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getMoviesFilm("бэтмен")
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
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
            bundle.putParcelable(OpenMoviesFragment.BUNDLE_EXTRA, null)
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.activity_main, OpenMoviesFragment.newInstance(bundle))
                ?.addToBackStack("")
                ?.commit()
        }
    }

    private fun getAdapterMyFriends(movieEntity: ArrayList<MovieEntity>) {
        adapterImages.setProfileInfo(movieEntity.asReversed())
    }

    private val adapterImages = AdapterMovies(onObjectListener)

    interface OnItemViewClickListener {
        fun onItemViewClick(movieEntity: MovieEntity)
    }

    private fun initToolbar() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.tool_bar)
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity!!.setSupportActionBar(toolbar)
        appCompatActivity.title = "Movies"
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_movies, menu)
        val search = menu.findItem(R.id.action_search)
        val searchText = search.actionView as SearchView
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getMoviesFilm(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return true
            }
        })
    }
}