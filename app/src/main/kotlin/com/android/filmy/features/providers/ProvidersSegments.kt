package com.android.filmy.features.providers

import com.android.filmy.Constants
import com.android.filmy.core.CarouselLayout
import com.android.filmy.core.Feed
import com.android.filmy.core.Segment
import com.android.filmy.core.SegmentContent

object ProvidersSegments {
    val providerSegment = Segment.CollectionSegment(
        layout = CarouselLayout(),
        content = SegmentContent.ProvidersSegment(feed = Feed.ProviderFeed.AllProvidersFeed)
    )

    fun getContentSegments(watchProviderId: String, watchProviderName: String): List<Segment> {
        return listOf(
            Segment.CollectionSegment(
                layout = CarouselLayout(),
                content = SegmentContent.MoviesSegment(
                    feed = Feed.MovieFeed.DiscoverFeed(
                        watchProviderId = watchProviderId,
                        watchProviderName = watchProviderName,
                        sortBy = Constants.MoviesSortType.POPULARITY_DESC.type,
                    )
                )
            ),
            Segment.CollectionSegment(
                layout = CarouselLayout(),
                content = SegmentContent.TvShowsSegment(
                    feed = Feed.TvFeed.DiscoverFeed(
                        watchProviderId = watchProviderId,
                        watchProviderName = watchProviderName,
                        sortBy = Constants.MoviesSortType.POPULARITY_DESC.type,
                    )
                )
            )
        )
    }
}