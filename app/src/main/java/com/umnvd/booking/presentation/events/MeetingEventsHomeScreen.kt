package com.umnvd.booking.presentation.events

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.umnvd.booking.core.navigation.navigations.EVENT_SCHEDULE_GRAPH_ROUTE
import com.umnvd.booking.core.navigation.navigations.meetingEventScheduleGraph
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MeetingEventsHomeScreen(
    onCreateClick: () -> Unit
) {
    val navController = rememberAnimatedNavController()

    Scaffold(
        topBar = {
            Text(text = "MeetingEventsHomeScreen")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick,
                elevation = FloatingActionButtonDefaults
                    .bottomAppBarFabElevation(defaultElevation = 0.dp),
                containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        AnimatedNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            startDestination = EVENT_SCHEDULE_GRAPH_ROUTE
        ) {
            meetingEventScheduleGraph(navController = navController)
        }
    }
}

@Preview
@Composable
private fun MeetingEventsHomeScreenPreview() {
    MeetingRoomBookingTheme {
        MeetingEventsHomeScreen {}
    }
}