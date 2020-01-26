package com.master8.shana

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.master8.shana.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .build()
                .create(TheMovieDatabaseApiService::class.java)

        MainScope().launch(Dispatchers.IO) {
            val responseBody = service.getMovie()
            Log.e("RXINFO", "after ${responseBody.string()}")
        }
    }
}

interface TheMovieDatabaseApiService {

    @GET("3/movie/550?api_key=${TMDbApiKey.API_KEY}")
    suspend fun getMovie() : ResponseBody
}