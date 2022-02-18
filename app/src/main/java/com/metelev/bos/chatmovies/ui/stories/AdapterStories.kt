package com.metelev.bos.chatmovies.ui.stories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metelev.bos.chatmovies.R
import com.metelev.bos.chatmovies.databinding.ItemMoviesBinding
import com.metelev.bos.chatmovies.domain.MovieEntity
import com.metelev.bos.chatmovies.domain.MoviesDbEntity
import com.metelev.bos.chatmovies.ui.latest.AdapterNewMovies
import com.metelev.bos.chatmovies.ui.latest.NewMoviesFragment
import com.squareup.picasso.Picasso

class AdapterStories (
    private var onItemViewClickListener: StoriesOfMoviesFragment.OnItemViewClickListener
) : RecyclerView.Adapter<AdapterStories.MainViewHolder>() {

    private var moviesList: List<MoviesDbEntity> = listOf()

    fun setProfileInfo(data: List<MoviesDbEntity>) {
        moviesList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MainViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movies, parent, false) as View
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int = moviesList.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMoviesBinding.bind(view)
        fun bind(moviesEntity: MoviesDbEntity) = with(binding) {
            nameMovie.text = moviesEntity.original_title.toString()
            ratingBar.rating = moviesEntity.vote_average?.toFloat()?.div(2) ?: rating
            releaseDate.text = release + moviesEntity.release_date.toString()
            Picasso.get()
                .load(patch + moviesEntity.poster_path)
                .into(imageMovie)
            root.setOnClickListener {
                onItemViewClickListener.onItemViewClick(moviesEntity)
            }
        }
    }

    companion object {
        private const val rating = 3f
        private const val release = "Release: "
        private const val patch = "https://image.tmdb.org/t/p/original"
    }
}