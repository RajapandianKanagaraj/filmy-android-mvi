package com.android.filmy.data

import com.android.filmy.core.Feed
import com.android.filmy.core.SegmentContent.MoviesSegment
import com.android.filmy.core.SegmentContent.TvShowsSegment
import com.android.filmy.core.SegmentContent.ActorsSegment
import com.android.filmy.model.response.MediaContent
import com.android.filmy.model.response.PaginatedResponse
import com.android.filmy.model.response.Person
import com.android.filmy.network.MovieApi
import com.android.filmy.network.PeopleApi
import com.android.filmy.network.TvApi
import javax.inject.Inject

interface FeedRepository {
    suspend fun getMovieFeed(segment: MoviesSegment): PaginatedResponse<MediaContent>
    suspend fun getTvShowsFeed(segment: TvShowsSegment): PaginatedResponse<MediaContent>
    suspend fun getActorsFeed(segment: ActorsSegment): PaginatedResponse<Person>
}

class FeedRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val tvApi: TvApi,
    private val peopleApi: PeopleApi,
) : FeedRepository {

    override suspend fun getMovieFeed(segment: MoviesSegment): PaginatedResponse<MediaContent> {
        return when (segment.feed) {
            is Feed.MovieFeed.NowPlayingFeed -> movieApi.getNowPlayingMovies(page = 1)
            is Feed.MovieFeed.PopularFeed -> movieApi.getPopularMovies(page = 1)
            is Feed.MovieFeed.TrendingFeed -> movieApi.getTopRatedMovies(page = 1)
            is Feed.MovieFeed.UpcomingFeed -> movieApi.getUpcomingMovies(page = 1)
            is Feed.MovieFeed.TopRatedFeed -> movieApi.getTopRatedMovies(page = 1)
        }
    }

    override suspend fun getTvShowsFeed(segment: TvShowsSegment): PaginatedResponse<MediaContent> {
       return when (segment.feed) {
           is Feed.TvFeed.PopularFeed -> tvApi.getPopularTvShows(page = 1)
           is Feed.TvFeed.TrendingFeed -> tvApi.getTopRatedTvShows(page = 1)
           is Feed.TvFeed.TopRatedFeed ->  tvApi.getTopRatedTvShows(page = 1)
           is Feed.TvFeed.OnTheAirFeed -> tvApi.getOnTheAirTvShows(page = 1)
           is Feed.TvFeed.AiringTodayFeed -> tvApi.getAiringTodayTvShows(page = 1)
       }
    }

    override suspend fun getActorsFeed(segment: ActorsSegment): PaginatedResponse<Person> {
        return when (segment.feed) {
            is Feed.ActorFeed.PopularFeed -> peopleApi.getPopularPeople(page = 1)
            is Feed.ActorFeed.TrendingFeed -> {
                peopleApi.getTrendingPeople(
                    timeWindow = segment.feed.timeWindow,
                    page = 1
                )
            }
        }
    }
}