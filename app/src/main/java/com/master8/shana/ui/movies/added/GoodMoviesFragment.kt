package com.master8.shana.ui.movies.added

import com.google.firebase.database.Query
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase

class GoodMoviesFragment : AddedMoviesFragment() {

    override fun getMoviesQuery(database: FirebaseRealtimeDatabase): Query {
        return database.goodMovies
    }

    override fun getTitle(): String = "Good Movies"
}