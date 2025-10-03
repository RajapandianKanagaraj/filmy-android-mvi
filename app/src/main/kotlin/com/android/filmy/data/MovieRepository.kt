package com.android.filmy.data

import com.android.filmy.model.Movie
import com.android.filmy.model.MovieResponse
import com.android.filmy.network.MovieApi
import javax.inject.Inject

interface MovieRepository {
    suspend fun getNowPlayingMovies(page: Int = 1): List<Movie>
}

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : MovieRepository {
    override suspend fun getNowPlayingMovies(page: Int): List<Movie> {
        val response: MovieResponse = movieApi.getNowPlayingMovies(page = page)
        return response.results
    }
}