package com.master8.shana.data.source.tmdb

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"

private const val TARGET_IMAGE_WIDTH = "w500"
private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/$TARGET_IMAGE_WIDTH"

private const val MEDIA_TYPE_MOVIE = "movie"
private const val MEDIA_TYPE_TV = "tv"

fun createTMDbApiService(): TMDbApiService {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TMDbApiService::class.java)
}

fun createTMDbAbsoluteImageUrl(relatedUrl: String): String = BASE_IMAGE_URL + relatedUrl

fun mediaTypeIsMovieOrTv(mediaType: String) = mediaType == MEDIA_TYPE_MOVIE || mediaType == MEDIA_TYPE_TV