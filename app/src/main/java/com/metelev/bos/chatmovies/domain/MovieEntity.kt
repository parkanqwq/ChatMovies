package com.metelev.bos.chatmovies.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieEntity(
    val id: Int?,
    val poster_path: String?,
    val original_title: String?,
    val vote_average: Double?,
    val release_date: String?,
    val original_language: String?,
    val runtime: Int?,
    val overview: String?,
    val backdrop_path: String?,
    val adult: Boolean?
) : Parcelable