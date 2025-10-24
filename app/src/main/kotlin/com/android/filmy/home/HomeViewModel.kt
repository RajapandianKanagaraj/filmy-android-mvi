package com.android.filmy.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.filmy.analytics.DatadogTracker
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
import com.android.filmy.core.SegmentLCEState
import com.android.filmy.core.SegmentRepository
import com.android.filmy.mvi.HomeAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val segmentRepository: SegmentRepository,
    val datadogTracker: DatadogTracker,
) : ViewModel() {
    val state: StateFlow<List<SegmentLCEState>> = segmentRepository.segmentState
    private val actions = MutableSharedFlow<HomeAction>()

    init {
        viewModelScope.launch {
            fetchNowPlayingMovies()
        }
    }

    private suspend fun fetchNowPlayingMovies() {
        segmentRepository.getContentSegments(
            listOf(
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
                    content = SegmentContent.ActorsSegment(feed = Feed.ActorFeed.PopularFeed),
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
                    layout = GridLayout(),
                    content = SegmentContent.TvShowsSegment(feed = Feed.TvFeed.PopularFeed),
                ),
                CollectionSegment(
                    layout = CarouselLayout(),
                    content = SegmentContent.ActorsSegment(feed = Feed.ActorFeed.TrendingFeed("day")),
                ),
                CollectionSegment(
                    layout = CarouselLayout(),
                    content = SegmentContent.TvShowsSegment(feed = Feed.TvFeed.TopRatedFeed),
                ),
                CollectionSegment(
                    layout = GridLayout(),
                    content = SegmentContent.TvShowsSegment(feed = Feed.TvFeed.OnTheAirFeed),
                ),
                CollectionSegment(
                    layout = GridLayout(),
                    content = SegmentContent.TvShowsSegment(feed = Feed.TvFeed.AiringTodayFeed),
                ),
                CollectionSegment(
                    layout = CarouselLayout(),
                    content = SegmentContent.ActorsSegment(feed = Feed.ActorFeed.TrendingFeed("week")),
                )
            )
        )
    }
}