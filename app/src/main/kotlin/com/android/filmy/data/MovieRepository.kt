package com.android.filmy.data

import com.android.filmy.core.MovieContentSegment
import com.android.filmy.core.Segment.MovieSegment
import com.android.filmy.model.MovieResponse
import com.android.filmy.network.MovieApi
import javax.inject.Inject

interface MovieRepository {

    suspend fun getMovieSegment(segment: MovieContentSegment): MovieSegment
}

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : MovieRepository {

    override suspend fun getMovieSegment(segment: MovieContentSegment): MovieSegment {
        return when (segment) {
            is MovieContentSegment.NowPlayingMovies -> getNowPlayingMovieSegment()
            is MovieContentSegment.PopularMovies -> getPopularMovies()
            is MovieContentSegment.TrendingMovies -> getTopRatedMovies()
            is MovieContentSegment.UpcomingMovies -> getUpcomingMovies()
        }
    }

    private suspend fun getNowPlayingMovieSegment(page: Int = 1): MovieSegment {
        val response: MovieResponse = movieApi.getNowPlayingMovies(page = page)
        val movieSegment = MovieSegment(
            title = "Now Playing Movies",
            movies = response.results
        )
        return movieSegment
    }

    private suspend fun getPopularMovies(page: Int = 1): MovieSegment {
        val response: MovieResponse = movieApi.getPopularMovies(page = page)
        val movieSegment = MovieSegment(
            title = "Popular Movies",
            movies = response.results
        )
        return movieSegment
    }

    private suspend fun getUpcomingMovies(page: Int = 1): MovieSegment {
        val response: MovieResponse = movieApi.getUpcomingMovies(page = page)
        val movieSegment = MovieSegment(
            title = "Upcoming Movies",
            movies = response.results
        )
        return movieSegment
    }

    private suspend fun getTopRatedMovies(page: Int = 1): MovieSegment {
        val response: MovieResponse = movieApi.getTopRatedMovies(page = page)
        val movieSegment = MovieSegment(
            title = "Top Rated Movies",
            movies = response.results
        )
        return movieSegment
    }
}