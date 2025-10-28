package com.android.filmy.core

sealed interface Segment {
    object BannerSegment: Segment
    data class CollectionSegment(
        val layout: CollectionLayout,
        val content: SegmentContent,
    ): Segment
}

sealed interface SegmentContent {
    class MoviesSegment(val feed: Feed.MovieFeed): SegmentContent
    class TvShowsSegment(val feed: Feed.TvFeed): SegmentContent
    class ActorsSegment(val feed: Feed.ActorFeed): SegmentContent
    class AllContentSegment(val feed: Feed.AllTrendingFeed): SegmentContent
}

sealed interface Feed {
    data class AllTrendingFeed(val timeWindow: String): Feed

    sealed interface MovieFeed : Feed {
        object NowPlayingFeed : MovieFeed
        object UpcomingFeed : MovieFeed
        object PopularFeed : MovieFeed
        data class TrendingFeed(val timeWindow: String) : MovieFeed
        object TopRatedFeed : MovieFeed
    }

    sealed interface TvFeed : Feed {
        object AiringTodayFeed : TvFeed
        object PopularFeed : TvFeed
        data class TrendingFeed(val timeWindow: String) : TvFeed
        object TopRatedFeed : TvFeed
        object OnTheAirFeed : TvFeed
    }

    sealed interface ActorFeed : Feed {
        object PopularFeed : ActorFeed
        data class TrendingFeed(val timeWindow: String): ActorFeed
    }
}