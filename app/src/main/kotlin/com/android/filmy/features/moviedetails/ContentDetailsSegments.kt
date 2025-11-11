package com.android.filmy.features.moviedetails

import com.android.filmy.core.Feed
import com.android.filmy.core.GridLayout
import com.android.filmy.core.Segment
import com.android.filmy.core.Segment.CollectionSegment
import com.android.filmy.core.SegmentContent.MoviesSegment

object ContentDetailsSegments {
    fun getSegment(contentId: String, contentType: String): List<Segment> {
        return if (contentType == "MOVIE") {
            listOf(
                CollectionSegment(
                    layout = GridLayout(),
                    content = MoviesSegment(feed = Feed.MovieFeed.SimilarFeed(contentId)),
                ),
                CollectionSegment(
                    layout = GridLayout(),
                    content = MoviesSegment(feed = Feed.MovieFeed.RecommendationFeed(contentId)),
                ),
            )
        } else {
            emptyList()
        }
    }
}