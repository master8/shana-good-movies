package com.master8.shana.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName
import com.master8.shana.data.source.tmdb.TMDbApiKey
import com.master8.shana.data.source.tmdb.TMDbApiServiceFactory
import com.master8.shana.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val service = Retrofit.Builder()
//                .baseUrl("https://api.themoviedb.org/3/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(TheMovieDatabaseApiService::class.java)

        MainScope().launch(Dispatchers.IO) {
            val result = TMDbApiServiceFactory.create().multipleSearch("vinland")
//            val movie = service.getMovie().convertPosterPath()
            Log.d("mv8", "after ${result}")
//            val popularMovies = service.getPopularMovies().results
//            Log.d("mv8", "movies $popularMovies")
        }
    }
}