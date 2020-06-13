package com.master8.shana.data.source.tmdb

import com.master8.shana.data.source.tmdb.dto.PostersDto
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
        @Query("language") language: String = "en",
        @Query("api_key") apiKey: String = TMDbApiKey.API_KEY
    ): TvDetailsResultDto

    @GET("movie/{id}/images")
    suspend fun getPosters(
        @Path("id")  movieId: Int,
        @Query("include_image_language") languages: String = "jp,ru,en",
        @Query("api_key") apiKey: String = TMDbApiKey.API_KEY
    ): PostersDto

    @GET("tv/{tv_id}/images")
    suspend fun getTvPosters(
        @Path("tv_id")  tvId: Int,
        @Query("api_key") apiKey: String = TMDbApiKey.API_KEY
    ): PostersDto

    @GET("tv/{tv_id}/season/{season_number}/images")
    suspend fun getSeasonPosters(
        @Path("tv_id")  tvId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("api_key") apiKey: String = TMDbApiKey.API_KEY
    ): PostersDto
}