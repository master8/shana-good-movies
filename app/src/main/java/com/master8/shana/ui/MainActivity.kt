package com.master8.shana.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName
import com.master8.shana.data.source.tmdb.TMDbApiKey
import com.master8.shana.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TheMovieDatabaseApiService::class.java)

        MainScope().launch(Dispatchers.IO) {
            val movie = service.getMovie().convertPosterPath()
            Log.d("mv8", "after ${movie}")
//            val popularMovies = service.getPopularMovies().results
//            Log.d("mv8", "movies $popularMovies")
        }
    }
}

interface TheMovieDatabaseApiService {

    @GET("tv/62104?api_key=${TMDbApiKey.API_KEY}&language=ru-Ru")
    suspend fun getMovie(): Movie

    @GET("movie/popular?api_key=${TMDbApiKey.API_KEY}")
    suspend fun getPopularMovies(): MoviesListDto
}

data class Movie(
        val id: Int,
        val adult: Boolean = false,
        val name: String = "",
        @SerializedName("original_name")
        val originalName: String = "",
        @SerializedName("poster_path")
        val posterPath: String = ""
) {

    fun convertPosterPath(): Movie {
        return Movie(id, adult, name, originalName, "https://image.tmdb.org/t/p/w500${posterPath}")
    }
}

data class MoviesListDto(
        val page: Int = 0,
        val results: List<Movie>
)