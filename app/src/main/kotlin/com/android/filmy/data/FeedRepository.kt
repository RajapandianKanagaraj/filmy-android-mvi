package com.android.filmy.data

import com.android.filmy.core.Feed
import com.android.filmy.core.SegmentContent
import com.android.filmy.core.SegmentContent.MoviesSegment
import com.android.filmy.core.SegmentContent.TvShowsSegment
import com.android.filmy.core.SegmentContent.ActorsSegment
import com.android.filmy.model.response.FeedItemResponse
import com.android.filmy.model.response.MediaContent
import com.android.filmy.model.response.PaginatedResponse
import com.android.filmy.model.response.Person
import com.android.filmy.model.response.Provider
import com.android.filmy.network.MovieApi
import com.android.filmy.network.PeopleApi
import com.android.filmy.network.ProvidersApi
import com.android.filmy.network.TvApi
import javax.inject.Inject

interface FeedRepository {
    suspend fun getMovieFeed(segment: MoviesSegment): PaginatedResponse<MediaContent>
    suspend fun getTvShowsFeed(segment: TvShowsSegment): PaginatedResponse<MediaContent>
    suspend fun getActorsFeed(segment: ActorsSegment): PaginatedResponse<Person>
    suspend fun getAllFeed(segment: SegmentContent.AllContentSegment): PaginatedResponse<FeedItemResponse>

    suspend fun getProviderFeed(segment: SegmentContent.ProvidersSegment): PaginatedResponse<Provider>
}

class FeedRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val tvApi: TvApi,
    private val peopleApi: PeopleApi,
    private val providersApi: ProvidersApi,
) : FeedRepository {

    override suspend fun getMovieFeed(segment: MoviesSegment): PaginatedResponse<MediaContent> {
        return when (segment.feed) {
            is Feed.MovieFeed.NowPlayingFeed -> movieApi.getNowPlayingMovies(page = 1)
            is Feed.MovieFeed.PopularFeed -> movieApi.getPopularMovies(page = 1)
            is Feed.MovieFeed.TrendingFeed -> movieApi.getTrendingMovies(segment.feed.timeWindow, page = 1)
            is Feed.MovieFeed.UpcomingFeed -> movieApi.getUpcomingMovies(page = 1)
            is Feed.MovieFeed.TopRatedFeed -> movieApi.getTopRatedMovies(page = 1)
        }
    }

    override suspend fun getTvShowsFeed(segment: TvShowsSegment): PaginatedResponse<MediaContent> {
       return when (segment.feed) {
           is Feed.TvFeed.PopularFeed -> tvApi.getPopularTvShows(page = 1)
           is Feed.TvFeed.TrendingFeed -> tvApi.getTrendingTvShows(segment.feed.timeWindow, page = 1)
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

    override suspend fun getAllFeed(segment: SegmentContent.AllContentSegment): PaginatedResponse<FeedItemResponse> {
       return movieApi.getTrendingAll(segment.feed.timeWindow, page = 1)
    }

    override suspend fun getProviderFeed(segment: SegmentContent.ProvidersSegment): PaginatedResponse<Provider> {
        return when (segment.feed) {
            is Feed.ProviderFeed.MovieProvidersFeed -> providersApi.getMovieProviders()
            is Feed.ProviderFeed.TvShowProvidersFeed -> providersApi.getTvProviders()
        }
    }
}