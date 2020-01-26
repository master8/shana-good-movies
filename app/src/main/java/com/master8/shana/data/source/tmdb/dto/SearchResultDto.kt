package com.master8.shana.data.source.tmdb.dto

import com.google.gson.annotations.SerializedName

data class SearchResultDto(
    val page: Int = 0,

    @SerializedName("total_results")
    val resultsCount: Int = 0,

    @SerializedName("total_pages")
    val pagesCount: Int = 0,

    val results: List<MediaDto>
)