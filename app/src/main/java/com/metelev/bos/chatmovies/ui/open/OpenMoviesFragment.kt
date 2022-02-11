package com.metelev.bos.chatmovies.ui.open

import android.os.Bundle
import com.metelev.bos.chatmovies.R
import com.metelev.bos.chatmovies.ui.base.BaseFragment

class OpenMoviesFragment : BaseFragment(R.layout.fragment_open_movies) {

    override fun onResume() {
        super.onResume()

    }

    companion object {

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