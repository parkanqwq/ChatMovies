package com.metelev.bos.chatmovies.ui

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.metelev.bos.chatmovies.R
import com.metelev.bos.chatmovies.ui.base.BaseFragment
import com.metelev.bos.chatmovies.ui.latest.NewMoviesFragment
import com.metelev.bos.chatmovies.ui.movies.MoviesFragment
import com.metelev.bos.chatmovies.ui.stories.StoriesOfMoviesFragment
import kotlinx.android.synthetic.main.fragment_switch.*

class SwitchFragment : BaseFragment(R.layout.fragment_switch) {

    override fun onResume() {
        super.onResume()

        view_pager.adapter = activity?.let { ViewPagerAdapter(it.supportFragmentManager) }
        tab_layout.setupWithViewPager(view_pager)
        setHighlightedTab(NEW_MOVIES)

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                setHighlightedTab(position)
            }
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
        })
    }


    private fun setHighlightedTab(position: Int) {
        val layoutInflater = LayoutInflater.from(context)

        tab_layout.getTabAt(NEW_MOVIES)?.customView = null
        tab_layout.getTabAt(FIND_MOVIES)?.customView = null
        tab_layout.getTabAt(HISTORY_MOVIES)?.customView = null

        when (position) {
            NEW_MOVIES -> {
                setNewMovies(layoutInflater)
            }
            FIND_MOVIES -> {
                setFindMovies(layoutInflater)
            }
            HISTORY_MOVIES -> {
                setHistoryMovies(layoutInflater)
            }
            else -> {
                setNewMovies(layoutInflater)
            }
        }
    }

    private fun setNewMovies(layoutInflater: LayoutInflater) {
        val profile =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_new_films, null)
        profile.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.purple_200
                )
            )
        tab_layout.getTabAt(NEW_MOVIES)?.customView = profile
        tab_layout.getTabAt(FIND_MOVIES)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_find, null)
        tab_layout.getTabAt(HISTORY_MOVIES)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_history, null)
    }

    private fun setFindMovies(layoutInflater: LayoutInflater) {
        val games =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_find, null)
        games.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.purple_200
                )
            )
        tab_layout.getTabAt(NEW_MOVIES)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_new_films, null)
        tab_layout.getTabAt(FIND_MOVIES)?.customView = games
        tab_layout.getTabAt(HISTORY_MOVIES)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_history, null)
    }

    private fun setHistoryMovies(layoutInflater: LayoutInflater) {
        val general =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_history, null)
        general.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.purple_200
                )
            )
        tab_layout.getTabAt(NEW_MOVIES)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_new_films, null)
        tab_layout.getTabAt(FIND_MOVIES)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_find, null)
        tab_layout.getTabAt(HISTORY_MOVIES)?.customView = general
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

        private val fragments = arrayOf(NewMoviesFragment(), MoviesFragment(), StoriesOfMoviesFragment())

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> fragments[NEW_MOVIES_FRAGMENT]
                1 -> fragments[FIND_MOVIES_FRAGMENT]
                2 -> fragments[HISTORY_MOVIES_FRAGMENT]
                else -> fragments[NEW_MOVIES_FRAGMENT]
            }
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return null
        }
    }

    companion object {
        private const val NEW_MOVIES = 0
        private const val FIND_MOVIES = 1
        private const val HISTORY_MOVIES = 2

        private const val NEW_MOVIES_FRAGMENT = 0
        private const val FIND_MOVIES_FRAGMENT = 1
        private const val HISTORY_MOVIES_FRAGMENT = 2
    }
}