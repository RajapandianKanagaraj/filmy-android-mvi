package com.android.filmy.network

import com.android.filmy.model.response.PaginatedResponse
import com.android.filmy.model.response.Provider
import retrofit2.http.GET
import retrofit2.http.Query

interface ProvidersApi {
    @GET("watch/providers/movie")
    suspend fun getMovieProviders(
        @Query("language") language: String = "en-US",
        @Query("watch_region") watchRegion: String = "US",
    ): PaginatedResponse<Provider>

    @GET("watch/providers/tv")
    suspend fun getTvProviders(
        @Query("language") language: String = "en-US",
        @Query("watch_region") watchRegion: String = "US",
    ): PaginatedResponse<Provider>
}