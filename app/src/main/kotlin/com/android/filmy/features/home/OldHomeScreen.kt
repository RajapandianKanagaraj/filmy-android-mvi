package com.android.filmy.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.filmy.analytics.InitRumView
import com.android.filmy.analytics.TrackingEvent
import com.android.filmy.core.SegmentLCEState
import com.android.filmy.model.CollectionDataModel
import com.android.filmy.model.SegmentDataModel
import com.android.filmy.ui.TopNavTab
import com.android.filmy.ui.TopNavTabs
import com.android.filmy.ui.TrackableContent
import com.android.filmy.ui.components.Carousel
import com.filmy.tracking.TrackingParam

@Composable
fun OldHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val datadogTracker = viewModel.datadogTracker
    val state by viewModel.state.collectAsState()

    InitRumView(
        viewKey = "HomeScreen",
        viewName = "Home",
        attributes = mapOf("screen_name" to "Home")
    )
    LaunchedEffect(Unit) {
        datadogTracker.trackPageEvent(TrackingEvent.SCREEN_VIEWED, mapOf("screen_name" to "Home"))
    }

    // Solution #1
    TrackableHomeScreen(
        modifier = modifier,
        state = state,
        trackingParams = viewModel.getTrackingParams(),
    ) {
        viewModel.onTabSelected(it)
    }

    // Solution #2
//    CustomModifierHomeScreen(
//        modifier = modifier,
//        state = state,
//        trackingParams = viewModel.getTrackingParams(),
//    ) {
//        viewModel.onTabSelected(it)
//    }
}

// Solution #1 - Using Compose Wrapper
@Composable
private fun TrackableHomeScreen(
    modifier: Modifier = Modifier,
    state: List<SegmentLCEState>,
    trackingParams: TrackingParam,
    onTabSelected: (TopNavTab) -> Unit = {},
) {
    val topNavTabs = remember { TopNavTabs.allTabs }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableStateOf(topNavTabs[selectedTabIndex]) }

    TrackableContent(
        trackingParam = trackingParams,
        modifier = modifier,
    ) {
        Column(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                topNavTabs.forEachIndexed { index, tab ->
                    ContentTab(topNavTab = tab, selectedTab = selectedTab) {
                        selectedTabIndex = index
                        selectedTab = tab
                        onTabSelected
                    }
                }
            }

            TabContent(
                tab = selectedTab,
                state = state,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

// Solution #2 - Using Compose Wrapper
//@Composable
//private fun CustomModifierHomeScreen(
//    modifier: Modifier = Modifier,
//    state: List<SegmentLCEState>,
//    trackingParams: TrackingParam,
//    onTabSelected: (TopNavTab) -> Unit = {},
//) {
//    val topNavTabs = remember { TopNavTabs.allTabs }
//
//    var selectedTabIndex by remember { mutableIntStateOf(0) }
//    var selectedTab by remember { mutableStateOf(topNavTabs[selectedTabIndex]) }
//
//    val localTrackingContext = LocalTrackingContext.current
//
//    val trackingSubject = remember {
//        TrackingSubject(
//            id = trackingParams.id,
//            name = trackingParams.name,
//            role = trackingParams.role,
//            metadata = trackingParams.metadata,
//            parentId = localTrackingContext?.id.orEmpty(),
//            parentName = localTrackingContext?.name.orEmpty(),
//            ancestorChain = localTrackingContext?.ancestorChain.orEmpty() + ":" + trackingParams.id
//        )
//    }
//
//    val newTrackingContext = remember(trackingSubject.id, trackingSubject.name) {
//        TrackingContext(
//            id = trackingSubject.id,
//            name = trackingSubject.name,
//            ancestorChain = trackingSubject.ancestorChain,
//        )
//    }
//
//    CompositionLocalProvider(LocalTrackingContext provides newTrackingContext) {
//        Column(
//            modifier = modifier
//                .dispatchOnAppear(trackingSubject = trackingSubject),
//        ) {
//            Row(
//                modifier = Modifier
//                    .padding(8.dp),
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                topNavTabs.forEachIndexed { index, tab ->
//                    ContentTab(topNavTab = tab, selectedTab = selectedTab) {
//                        selectedTabIndex = index
//                        selectedTab = tab
//                        onTabSelected
//                    }
//                }
//            }
//
//            TabContent(
//                tab = selectedTab,
//                state = state,
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//    }
//}

@Composable
fun ContentTab(
    topNavTab: TopNavTab,
    selectedTab: TopNavTab,
    onTabSelected: (TopNavTab) -> Unit = {},
) {
    val bgColor = if (selectedTab == topNavTab) Color.Red else Color.LightGray

    Box(
        modifier = Modifier
            .height(32.dp)
            .wrapContentWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor, shape = RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true)
            ) {
                onTabSelected(topNavTab)
            }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            topNavTab.title,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 12.dp),
            color = Color.White,
        )
    }
}

@Composable
fun TabContent(
    tab: TopNavTab,
    state: List<SegmentLCEState>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(state) { segment ->
            RenderState(segment)
        }
    }
}

@Composable
fun RenderState(segmentState: SegmentLCEState) {
    if (segmentState.isLoading) {
        SegmentLoading()
    } else if (segmentState.error != null) {
        SegmentError()
    } else {
        RenderSegment(
            segment = segmentState.data,
        )
    }
}

@Composable
fun RenderSegment(segment: SegmentDataModel?) {
    segment?.let {
        if (segment is CollectionDataModel) {
            Carousel(
                dataModel = segment,
                items = segment.feeds,
            )
        }
    }
}

@Composable
fun SegmentLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(8.dp)
    ) {
        Text("Loading...")
    }
}

@Composable
fun SegmentError() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(8.dp)
    ) {

        Text("Error")
    }
}