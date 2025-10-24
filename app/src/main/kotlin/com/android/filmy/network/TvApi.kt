package com.android.filmy.network

import com.android.filmy.model.response.MediaContent
import com.android.filmy.model.response.PaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TvApi {
    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): PaginatedResponse<MediaContent>

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): PaginatedResponse<MediaContent>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShows(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): PaginatedResponse<MediaContent>

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShows(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): PaginatedResponse<MediaContent>
}