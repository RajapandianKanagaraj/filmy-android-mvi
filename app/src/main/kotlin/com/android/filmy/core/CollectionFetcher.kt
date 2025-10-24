package com.android.filmy.core

import com.android.filmy.data.FeedRepository
import javax.inject.Inject
import com.android.filmy.core.Segment.CollectionSegment
import com.android.filmy.parsers.MediaContentParser
import com.android.filmy.parsers.PersonParser

class CollectionFetcher @Inject constructor(
    val feedRepository: FeedRepository,
    val paginatedCollectionParser: PaginatedCollectionParser,
    val mediaContentParser: MediaContentParser,
    val personParser: PersonParser,
): ContentFetcher<CollectionSegment> {
    override suspend fun fetchContent(segment: CollectionSegment): SegmentLCEState {
        return when (segment.content) {
            is SegmentContent.MoviesSegment -> {
                val movieSegment = feedRepository.getMovieFeed(segment.content)
                val dataModel = paginatedCollectionParser.parse(response = movieSegment, itemsParser = mediaContentParser)
                SegmentLCEState(
                    isLoading = false,
                    data = dataModel.updateTitle(getCollectionTitle(segment.content.feed)),
                )
            }

            is SegmentContent.TvShowsSegment -> {
                val segmentFeed = feedRepository.getTvShowsFeed(segment.content)
                val dataModel = paginatedCollectionParser.parse(response = segmentFeed, itemsParser = mediaContentParser)
                SegmentLCEState(
                    isLoading = false,
                    data = dataModel.updateTitle(getCollectionTitle(segment.content.feed)),
                )
            }

            is SegmentContent.ActorsSegment -> {
                val segmentFeed = feedRepository.getActorsFeed(segment.content)
                val dataModel = paginatedCollectionParser.parse(response = segmentFeed, itemsParser = personParser)
                SegmentLCEState(
                    isLoading = false,
                    data = dataModel.updateTitle(getCollectionTitle(segment.content.feed)),
                )
            }
        }
    }

    private fun getCollectionTitle(feed: Feed): String {
        return when (feed) {
            is Feed.MovieFeed.TopRatedFeed -> "Top Rated Movies"
            is Feed.MovieFeed.NowPlayingFeed -> "Now Playing Movies"
            is Feed.MovieFeed.UpcomingFeed -> "Upcoming Movies"
            is Feed.MovieFeed.PopularFeed -> "Popular Movies"
            is Feed.TvFeed.TopRatedFeed -> "Top Rated TV Shows"
            is Feed.TvFeed.PopularFeed -> "Popular TV Shows"
            is Feed.TvFeed.OnTheAirFeed -> "On The Air TV Shows"
            is Feed.TvFeed.AiringTodayFeed -> "Airing Today TV Shows"
            is Feed.ActorFeed.PopularFeed -> "Popular Actor"
            is Feed.MovieFeed.TrendingFeed -> "Trending Movies"
            is Feed.TvFeed.TrendingFeed -> "Trending TV Shows"
            is Feed.ActorFeed.TrendingFeed -> {
                if (feed.timeWindow == "day") {
                    "Trending Actors Today"
                } else if (feed.timeWindow == "week") {
                    "Trending Actors This Week"
                } else {
                    "Trending Actors"
                }
            }
        }
    }
}
