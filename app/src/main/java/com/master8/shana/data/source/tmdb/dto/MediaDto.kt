package com.master8.shana.data.source.tmdb.dto

import com.google.gson.annotations.SerializedName

data class MediaDto(

    val id: Int? = null,

    @SerializedName("media_type")
    val mediaType: String,

    @SerializedName("name", alternate = ["title"])
    val name: String = "",

    @SerializedName("original_name", alternate = ["original_title"])
    val originalName: String = "",

    @SerializedName("release_date", alternate = ["first_air_date"])
    val releaseDate: String? = null,

    @SerializedName("poster_path")
    val poster: String? = null
)