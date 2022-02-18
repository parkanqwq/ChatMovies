package com.metelev.bos.chatmovies.ui.open

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metelev.bos.chatmovies.R
import com.metelev.bos.chatmovies.databinding.ItemActorBinding
import com.metelev.bos.chatmovies.domain.ActorEntity
import com.squareup.picasso.Picasso

class AdapterActor : RecyclerView.Adapter<AdapterActor.MainViewHolder>() {

    private var moviesList: List<ActorEntity> = listOf()

    fun setProfileInfo(data: List<ActorEntity>) {
        moviesList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MainViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_actor, parent, false) as View
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int = moviesList.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemActorBinding.bind(view)
        fun bind(moviesEntity: ActorEntity) = with(binding) {
            actorTextView.text = moviesEntity.name.toString()
            Picasso.get()
                .load(patch + moviesEntity.profile_path)
                .into(actorImageView)
        }
    }

    companion object {
        private const val patch = "https://image.tmdb.org/t/p/original"
    }
}