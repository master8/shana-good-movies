package com.master8.shana.data.source.tmdb

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TMDbApiServiceFactory {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    fun create(): TMDbApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDbApiService::class.java)
    }
}