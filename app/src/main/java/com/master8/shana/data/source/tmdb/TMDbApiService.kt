package com.master8.shana.data.source.tmdb

import com.master8.shana.data.source.tmdb.dto.SearchResultDto
import com.master8.shana.data.source.tmdb.dto.TvDetailsResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApiService {

    @GET("search/multi")
    suspend fun multipleSearch(
        @Query("query") query: String,
        @Query("language") language: String = "ru",
        @Query("api_key") apiKey: String = TMDbApiKey.API_KEY
    ): SearchResultDto

    @GET("tv/{id}")
    suspend fun getTvDetails(
        @Path("id") tvId: Int,
        @Query("api_key") apiKey: String = TMDbApiKey.API_KEY
    ): TvDetailsResultDto
}