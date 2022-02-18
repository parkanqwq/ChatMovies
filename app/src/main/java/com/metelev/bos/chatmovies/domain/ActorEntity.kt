package com.metelev.bos.chatmovies.domain


data class ActorEntity(
    val id: Long,
    val name: String,
    val profile_path: String? = null,
)
