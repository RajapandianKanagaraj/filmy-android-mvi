package com.android.filmy.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.mvi.NavAction

enum class NavIconType {
    NONE, BACK
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String = "Filmy",
    navIconType: NavIconType = NavIconType.NONE,
    onSearchClick: () -> Unit = {},
) {
    val actionDispatcher = LocalActionDispatcher.current
    val navIcon = when (navIconType) {
        NavIconType.BACK -> Icons.AutoMirrored.Filled.ArrowBack
        else -> null
    }

    Column(modifier = modifier) {
        TopAppBar(
            title = {
                Text(title)
            },
            navigationIcon = {
                navIcon?.let {
                    IconButton(onClick = {
                        actionDispatcher.dispatch(NavAction.navigateBack)
                    }) {
                        Icon(
                            imageVector = it,
                            contentDescription = "Back"
                        )
                    }
                }
            },
            actions = {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            },
        )
    }
}