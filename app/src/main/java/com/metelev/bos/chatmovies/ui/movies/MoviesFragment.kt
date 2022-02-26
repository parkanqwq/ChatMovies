package com.metelev.bos.chatmovies.ui.movies

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
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
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.SuccessMovies -> {
                getAdapterMyFriends(data.data)
                progress_circular.visibility = View.GONE
            }
            is AppState.Loading -> {
                progress_circular.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                Toast.makeText(context, (data.error), Toast.LENGTH_SHORT).show()
                progress_circular.visibility = View.GONE
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

    private val adapterImages = AdapterMovies(onObjectListener)

    interface OnItemViewClickListener {
        fun onItemViewClick(movieEntity: MovieEntity)
    }

    private fun initToolbar() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.find_tool_bar)
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity!!.setSupportActionBar(toolbar)
        appCompatActivity.title = "Поиск фильма"
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
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return true
            }
        })
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onPause() {
        super.onPause()
        initToolbar()
    }
}