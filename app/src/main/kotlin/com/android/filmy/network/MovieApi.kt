package com.android.filmy.network

import com.android.filmy.model.response.FeedItemResponse
import com.android.filmy.model.response.MediaContent
import com.android.filmy.model.response.PaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): PaginatedResponse<MediaContent>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): PaginatedResponse<MediaContent>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): PaginatedResponse<MediaContent>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): PaginatedResponse<MediaContent>

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): PaginatedResponse<MediaContent>

    @GET("trending/all/{time_window}")
    suspend fun getTrendingAll(
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): PaginatedResponse<FeedItemResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: String,
        @Query("language") language: String = "en-US"
    ): PaginatedResponse<MediaContent>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendationsMovies(
        @Path("movie_id") movieId: String,
        @Query("language") language: String = "en-US"
    ): PaginatedResponse<MediaContent>
}