package com.master8.shana.data.source.tmdb

object TMDbImageUrlFactory {

    private const val TARGET_IMAGE_WIDTH = "w500"
    private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/$TARGET_IMAGE_WIDTH"

    fun createAbsoluteUrl(relatedUrl: String): String = BASE_IMAGE_URL + relatedUrl
}