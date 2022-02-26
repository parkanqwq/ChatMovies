package com.metelev.bos.chatmovies.ui

import androidx.fragment.app.Fragment
import com.metelev.bos.chatmovies.R
import com.metelev.bos.chatmovies.ui.base.BaseFragment
import com.metelev.bos.chatmovies.ui.latest.NewMoviesFragment
import com.metelev.bos.chatmovies.ui.movies.MoviesFragment
import com.metelev.bos.chatmovies.ui.stories.StoriesOfMoviesFragment
import kotlinx.android.synthetic.main.fragment_switch.*


class SwitchFragment : BaseFragment(R.layout.fragment_switch) {

    override fun onResume() {
        super.onResume()

        navigatorSearch(NewMoviesFragment())
        navigationShits()
    }

    private fun navigatorSearch(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_view, fragment)
            ?.commitAllowingStateLoss()
    }

    private fun navigationShits() {
        bottom_navigation_view.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_note -> {
                    navigatorSearch(NewMoviesFragment())
                    true
                }
                R.id.bottom_view_date_note -> {
                    navigatorSearch(MoviesFragment())
                    true
                }
                R.id.bottom_view_settings -> {
                    navigatorSearch(StoriesOfMoviesFragment())
                    true
                }
                else -> {
                    navigatorSearch(NewMoviesFragment())
                    true
                }
            }
        }
    }
}