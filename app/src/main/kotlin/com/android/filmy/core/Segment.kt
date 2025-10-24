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
}

sealed interface Feed {
    sealed interface MovieFeed : Feed {
        object NowPlayingFeed : MovieFeed
        object UpcomingFeed : MovieFeed
        object PopularFeed : MovieFeed
        object TrendingFeed : MovieFeed
        object TopRatedFeed : MovieFeed
    }

    sealed interface TvFeed : Feed {
        object NowPlayingFeed : TvFeed
        object PopularFeed : TvFeed
        object TrendingFeed : TvFeed
        object TopRatedFeed : TvFeed
    }

    sealed interface ActorFeed : Feed {
        object PopularFeed : ActorFeed
    }
}

//sealed interface MovieContentSegment : ContentSegment {
//    object NowPlayingMovies : MovieContentSegment
//    object UpcomingMovies : MovieContentSegment
//    object PopularMovies : MovieContentSegment
//    object TrendingMovies : MovieContentSegment
//}
//
//sealed interface TvContentSegment : ContentSegment {
//    object NowPlayingSeries : TvContentSegment
//    object PopularSeries : TvContentSegment
//    object TrendingSeries : TvContentSegment
//    object UpcomingSeries : TvContentSegment
//}