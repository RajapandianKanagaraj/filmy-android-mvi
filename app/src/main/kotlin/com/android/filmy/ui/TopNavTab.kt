package com.android.filmy.ui

data class TopNavTab(
    val id: String,
    val title: String,
    val route: String,
)

object TopNavTabs {
    val allTab = TopNavTab("all", "All", "all-section")
    val movieTab = TopNavTab("movie", "Movies", "movie-section")
    val tvTab = TopNavTab("tv", "Series", "tv-section")

    val allTabs = listOf(allTab, movieTab, tvTab)
}