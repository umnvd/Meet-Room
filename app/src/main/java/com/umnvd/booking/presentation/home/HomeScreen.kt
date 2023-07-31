package com.umnvd.booking.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EventNote
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.umnvd.booking.R
import com.umnvd.booking.core.navigation.navigations.EVENTS_GRAPH_ROUTE
import com.umnvd.booking.core.navigation.navigations.PROFILE_ROUTE
import com.umnvd.booking.core.navigation.navigations.ROOMS_GRAPH_ROUTE
import com.umnvd.booking.core.navigation.navigations.meetingEventsGraph
import com.umnvd.booking.core.navigation.navigations.meetingRoomsGraph
import com.umnvd.booking.core.navigation.navigations.profile
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.presentation.home.models.NavigationItemUiModel

@OptIn(ExperimentalAnimationApi::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    onSignedOut: () -> Unit
) {
    val navController = rememberAnimatedNavController()
    val navigationBarItems = listOf(
        NavigationItemUiModel(
            titleId = R.string.events_tab,
            icon = Icons.Outlined.EventNote,
            route = EVENTS_GRAPH_ROUTE,
        ),
        NavigationItemUiModel(
            titleId = R.string.rooms_tab,
            icon = Icons.Outlined.MeetingRoom,
            route = ROOMS_GRAPH_ROUTE,
        ),
        NavigationItemUiModel(
            titleId = R.string.profile_tab,
            icon = Icons.Outlined.Person,
            route = PROFILE_ROUTE,
        ),
    )

    val imeVisible by rememberImeVisible() // true or false
    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        bottomBar = {
            AnimatedVisibility(
                visible = !imeVisible,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    tonalElevation = 0.dp,
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    navigationBarItems.forEach { item ->
                        val title = stringResource(id = item.titleId)
                        NavigationBarItem(
                            icon = { Icon(imageVector = item.icon, contentDescription = title) },
                            label = { Text(title) },
                            selected = currentDestination?.hierarchy
                                ?.any { it.route == item.route } == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.surface,
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                            ),
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AnimatedNavHost(
            navController = navController,
            startDestination = EVENTS_GRAPH_ROUTE,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .imePadding(),
        ) {
            meetingEventsGraph(navController = navController)
            meetingRoomsGraph(navController = navController)
            profile(onSignedOut = onSignedOut)
        }
    }
}

@Composable
private fun rememberImeVisible(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MeetingRoomBookingTheme {
        HomeScreen(onSignedOut = {})
    }
}