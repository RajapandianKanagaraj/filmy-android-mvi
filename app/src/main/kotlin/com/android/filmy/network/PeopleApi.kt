package com.android.filmy.network

import com.android.filmy.model.response.PaginatedResponse
import com.android.filmy.model.response.Person
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {
    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): PaginatedResponse<Person>

    @GET("trending/person/{time_window}")
    suspend fun getTrendingPeople(
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): PaginatedResponse<Person>
}