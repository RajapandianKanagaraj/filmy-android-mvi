package com.android.filmy.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.filmy.ui.TopNavTabs
import com.filmy.core.tracking.annotations.Trackable

@Trackable(
    id = "home_screen",
    name = "Home Screen",
    role = "Screen",
    metadata = "Home Screen",
)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val topNavTabs = remember { TopNavTabs.allTabs }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableStateOf(topNavTabs[selectedTabIndex]) }

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
                    viewModel.onTabSelected(it)
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
