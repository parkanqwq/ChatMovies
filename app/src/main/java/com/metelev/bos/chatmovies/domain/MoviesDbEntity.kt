package com.metelev.bos.chatmovies.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class MoviesDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "poster_path") val poster_path: String,
    @ColumnInfo(name = "original_title") val original_title: String,
    @ColumnInfo(name = "vote_average") val vote_average: String,
    @ColumnInfo(name = "release_date") val release_date: String,
    @ColumnInfo(name = "original_language") val original_language: String,
    @ColumnInfo(name = "runtime") val runtime: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "backdrop_path") val backdrop_path: String,
    @ColumnInfo(name = "adult") val adult: String
) : Parcelable
