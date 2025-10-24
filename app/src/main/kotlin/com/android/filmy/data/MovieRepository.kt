package com.android.filmy.data

import com.android.filmy.core.Feed
import com.android.filmy.core.SegmentContent.MoviesSegment
import com.android.filmy.core.SegmentData.MovieSegmentData
import com.android.filmy.model.MovieResponse
import com.android.filmy.network.MovieApi
import javax.inject.Inject

interface MovieRepository {

    suspend fun getMovieSegment(segment: MoviesSegment): MovieSegmentData
}

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : MovieRepository {

    override suspend fun getMovieSegment(segment: MoviesSegment): MovieSegmentData {
        return when (segment.feed) {
            is Feed.MovieFeed.NowPlayingFeed -> getNowPlayingMovieSegment()
            is Feed.MovieFeed.PopularFeed -> getPopularMovies()
            is Feed.MovieFeed.TrendingFeed -> getTopRatedMovies()
            is Feed.MovieFeed.UpcomingFeed -> getUpcomingMovies()
            is Feed.MovieFeed.TopRatedFeed -> getTopRatedMovies()
        }
    }

    private suspend fun getNowPlayingMovieSegment(page: Int = 1): MovieSegmentData {
        val response: MovieResponse = movieApi.getNowPlayingMovies(page = page)
        val movieSegment = MovieSegmentData(
            title = "Now Playing Movies",
            movies = response.results
        )
        return movieSegment
    }

    private suspend fun getPopularMovies(page: Int = 1): MovieSegmentData {
        val response: MovieResponse = movieApi.getPopularMovies(page = page)
        val movieSegment = MovieSegmentData(
            title = "Popular Movies",
            movies = response.results
        )
        return movieSegment
    }

    private suspend fun getUpcomingMovies(page: Int = 1): MovieSegmentData {
        val response: MovieResponse = movieApi.getUpcomingMovies(page = page)
        val movieSegment = MovieSegmentData(
            title = "Upcoming Movies",
            movies = response.results
        )
        return movieSegment
    }

    private suspend fun getTopRatedMovies(page: Int = 1): MovieSegmentData {
        val response: MovieResponse = movieApi.getTopRatedMovies(page = page)
        val movieSegment = MovieSegmentData(
            title = "Top Rated Movies",
            movies = response.results
        )
        return movieSegment
    }
}