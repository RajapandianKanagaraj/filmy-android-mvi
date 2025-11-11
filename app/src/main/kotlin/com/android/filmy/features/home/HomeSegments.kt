package com.android.filmy.features.home

import com.android.filmy.core.CarouselLayout
import com.android.filmy.core.Feed
import com.android.filmy.core.Feed.MovieFeed.NowPlayingFeed
import com.android.filmy.core.Feed.MovieFeed.PopularFeed
import com.android.filmy.core.Feed.MovieFeed.TopRatedFeed
import com.android.filmy.core.Feed.MovieFeed.UpcomingFeed
import com.android.filmy.core.GridLayout
import com.android.filmy.core.Segment.CollectionSegment
import com.android.filmy.core.SegmentContent
import com.android.filmy.core.SegmentContent.MoviesSegment

object HomeSegments {
    val allSegments = listOf(
        CollectionSegment(
            layout = CarouselLayout(),
            content = MoviesSegment(feed = NowPlayingFeed),
        ),
        CollectionSegment(
            layout = CarouselLayout(),
            content = MoviesSegment(feed = TopRatedFeed),
        ),
        CollectionSegment(
            layout = CarouselLayout(),
            content = SegmentContent.ProvidersSegment(feed = Feed.ProviderFeed.TvShowProvidersFeed),
        ),
        CollectionSegment(
            layout = CarouselLayout(),
            content = SegmentContent.ActorsSegment(feed = Feed.ActorFeed.PopularFeed),
        ),
        CollectionSegment(
            layout = CarouselLayout(),
            content = SegmentContent.AllContentSegment(feed = Feed.AllTrendingFeed("day"))
        ),
        CollectionSegment(
            layout = GridLayout(),
            content = MoviesSegment(feed = UpcomingFeed),
        ),
        CollectionSegment(
            layout = GridLayout(),
            content = MoviesSegment(feed = PopularFeed),
        ),
        CollectionSegment(
            layout = CarouselLayout(),
            content = SegmentContent.AllContentSegment(feed = Feed.AllTrendingFeed("week"))
        ),
        CollectionSegment(
            layout = GridLayout(),
            content = SegmentContent.TvShowsSegment(feed = Feed.TvFeed.PopularFeed),
        ),
        CollectionSegment(
            layout = CarouselLayout(),
            content = SegmentContent.TvShowsSegment(feed = Feed.TvFeed.TrendingFeed("week")),
        ),
        CollectionSegment(
            layout = CarouselLayout(),
            content = SegmentContent.ActorsSegment(feed = Feed.ActorFeed.TrendingFeed("day")),
        ),
        CollectionSegment(
            layout = CarouselLayout(),
            content = MoviesSegment(feed = Feed.MovieFeed.TrendingFeed("day")),
        ),
        CollectionSegment(
            layout = CarouselLayout(),
            content = SegmentContent.TvShowsSegment(feed = Feed.TvFeed.TopRatedFeed),
        ),
        CollectionSegment(
            layout = CarouselLayout(),
            content = SegmentContent.ProvidersSegment(feed = Feed.ProviderFeed.MovieProvidersFeed),
        ),
        CollectionSegment(
            layout = GridLayout(),
            content = SegmentContent.TvShowsSegment(feed = Feed.TvFeed.OnTheAirFeed),
        ),

        CollectionSegment(
            layout = CarouselLayout(),
            content = MoviesSegment(feed = Feed.MovieFeed.TrendingFeed("week")),
        ),
        CollectionSegment(
            layout = GridLayout(),
            content = SegmentContent.TvShowsSegment(feed = Feed.TvFeed.AiringTodayFeed),
        ),
        CollectionSegment(
            layout = CarouselLayout(),
            content = SegmentContent.ActorsSegment(feed = Feed.ActorFeed.TrendingFeed("week")),
        ),
        CollectionSegment(
            layout = CarouselLayout(),
            content = SegmentContent.TvShowsSegment(feed = Feed.TvFeed.TrendingFeed("day")),
        ),
        CollectionSegment(
            layout = GridLayout(),
            content = MoviesSegment(feed = Feed.MovieFeed.SimilarFeed("1197137")),
        ),
        CollectionSegment(
            layout = GridLayout(),
            content = MoviesSegment(feed = Feed.MovieFeed.RecommendationFeed("1197137")),
        ),
    )
}