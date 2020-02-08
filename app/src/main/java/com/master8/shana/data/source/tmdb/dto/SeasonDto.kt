package com.master8.shana.data.source.tmdb.dto

import com.google.gson.annotations.SerializedName

data class SeasonDto(
    val id: Int,
    val name: String,

    @SerializedName("air_date")
    val releaseDate: String? = null,

    @SerializedName("episode_count")
    val episodeCount: Int,

    @SerializedName("season_number")
    val seasonNumber: Int,

    @SerializedName("poster_path")
    val poster: String? = null
)