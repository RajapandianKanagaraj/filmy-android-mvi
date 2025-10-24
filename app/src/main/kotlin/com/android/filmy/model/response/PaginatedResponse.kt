package com.android.filmy.model.response

import com.squareup.moshi.Json

data class PaginatedResponse<T>(
    val page: Int,
    @param: Json(name = "total_pages") val totalPages: Int,
    @param: Json(name = "total_results") val totalResults: Int,
    val results: List<T>
): DataResponse
