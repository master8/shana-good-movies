package com.master8.shana.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.master8.shana.data.repository.converters.parseMovie
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabaseImpl
import com.master8.shana.databinding.ActivityMainBinding
import com.master8.shana.domain.entity.Movie
import com.master8.shana.ui.movies.MoviesFirebaseAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        MainScope().launch {
//            val repository = MoviesRepositoryImpl(createTMDbApiService(), FirebaseRealtimeDatabaseImpl())
//            val search = SearchMoviesUseCase(repository)
//            val movies = search("star wars")
//            Log.d("mv8", "after ${movies}")
//
//            val prepareMovieToAddUseCase = PrepareMovieToAddUseCase()
//
//            val addGoodMovie = AddGoodMovieUseCase(repository, prepareMovieToAddUseCase)
//            addGoodMovie(movies[0])
//            addGoodMovie(movies[1])
//
//            val addNeedToWatchMovie = AddNeedToWatchMovieUseCase(repository, prepareMovieToAddUseCase)
//            addNeedToWatchMovie(movies.last())
//        }

        val firebaseRealtimeDatabase: FirebaseRealtimeDatabase = FirebaseRealtimeDatabaseImpl()

        val options = FirebaseRecyclerOptions.Builder<Movie>()
            .setQuery(firebaseRealtimeDatabase.goodMovies.orderByChild("dateAdded"), ::parseMovie)
            .setLifecycleOwner(this)
            .build()

        val adapter = MoviesFirebaseAdapter(options)

        val layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        binding.listMovies.layoutManager = layoutManager
        binding.listMovies.adapter = adapter
    }
}