package com.master8.shana.data.source.tmdb

import android.net.Uri
import androidx.core.text.isDigitsOnly
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_URL = "https://api.themoviedb.org/3/"

private const val TARGET_IMAGE_WIDTH = "w500"
private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/$TARGET_IMAGE_WIDTH"

private const val MEDIA_TYPE_MOVIE = "movie"
private const val MEDIA_TYPE_TV = "tv"

private val dateFormatter by lazy { SimpleDateFormat("yyyy-MM-dd") }

fun createTMDbApiService(): TMDbApiService {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TMDbApiService::class.java)
}

fun createTMDbAbsoluteImageUri(relatedUrl: String?): Uri? {
    return relatedUrl?.let { Uri.parse(BASE_IMAGE_URL + it) }
}

fun mediaTypeIsMovie(mediaType: String) = mediaType == MEDIA_TYPE_MOVIE
fun mediaTypeIsTV(mediaType: String) = mediaType == MEDIA_TYPE_TV

fun getYearFromTMDbDate(date: String): Int {
    if (date.isBlank()) {
        return 0
    }

    return Calendar.getInstance()
        .apply { time = dateFormatter.parse(date) }
        .get(Calendar.YEAR)
}