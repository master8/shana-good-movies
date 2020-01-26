package com.master8.shana.data.source.tmdb

import com.master8.shana.data.source.tmdb.dto.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbApiService {

    @GET("search/multi")
    suspend fun multipleSearch(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = TMDbApiKey.API_KEY
    ): SearchResultDto
}