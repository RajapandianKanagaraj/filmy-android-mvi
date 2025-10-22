package com.android.filmy.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.mvi.NavAction

enum class NavIconType {
    NONE, BACK
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    navIconType: NavIconType = NavIconType.NONE,
) {
    val actionDispatcher = LocalActionDispatcher.current
    val navIcon = when (navIconType) {
        NavIconType.BACK -> Icons.AutoMirrored.Filled.ArrowBack
        else -> null
    }

    TopAppBar(
        title = {
            Text("Filmy")
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
        actions = {},
    )
}