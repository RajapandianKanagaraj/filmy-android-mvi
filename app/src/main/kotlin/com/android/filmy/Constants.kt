package com.android.filmy

object Constants {
    enum class MoviesSortType(val type: String) {
        POPULARITY_DESC("popularity.desc"),
        POPULARITY_ASC("popularity.asc"),
        VOTE_AVERAGE_DESC("vote_average.desc"),
        VOTE_AVERAGE_ASC("vote_average.asc"),
        RELEASE_DATE_DESC("primary_release_date.desc")
    }
}