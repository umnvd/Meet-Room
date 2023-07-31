package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.core.ui.viewmodels.SyncViewModel
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.presentation.events.creation.MeetingEventCreationScreen
import com.umnvd.booking.presentation.events.event.MeetingEventScreen
import com.umnvd.booking.presentation.events.home.MeetingEventsHomeScreen
import com.umnvd.booking.presentation.events.user.UserMeetingEventListScreen

const val EVENTS_GRAPH_ROUTE = "events_graph"
const val EVENT_ROUTE_UID_KEY = "uid"
const val EVENTS_HOME_ROUTE = "events"

private const val EVENT_ROUTE_BASE = "event"
private const val EVENT_ROUTE = "$EVENT_ROUTE_BASE/{$EVENT_ROUTE_UID_KEY}"
private const val CREATE_EVENT_ROUTE = "create_event"
private const val USER_EVENT_LIST_ROUTE = "user_event_list"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.meetingEventsGraph(
    navController: NavHostController,
) {
    navigation(startDestination = EVENTS_HOME_ROUTE, route = EVENTS_GRAPH_ROUTE) {
        composable(route = EVENTS_HOME_ROUTE) {
            MeetingEventsHomeScreen(
                onCreateClick = { navController.navigate(CREATE_EVENT_ROUTE) },
                onMyEventsClick = { navController.navigate(USER_EVENT_LIST_ROUTE) },
                navigateToEvent = navController::navigateToEvent,
                homeNavController = navController,
            )
        }
        composable(
            route = EVENT_ROUTE,
            arguments = listOf(navArgument(EVENT_ROUTE_UID_KEY) { type = NavType.StringType })
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(EVENTS_HOME_ROUTE)
            }
            val syncViewModel = hiltViewModel<SyncViewModel>(parentEntry)

            MeetingEventScreen(
                syncViewModel = syncViewModel,
                onSaved = navController::popBackStack,
                onDeleted = navController::popBackStack,
                onBackClick = navController::popBackStack,
            )
        }
        composable(route = CREATE_EVENT_ROUTE) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(EVENTS_HOME_ROUTE)
            }
            val syncViewModel = hiltViewModel<SyncViewModel>(parentEntry)

            MeetingEventCreationScreen(
                syncViewModel = syncViewModel,
                onCreated = navController::popBackStack,
                onBackClick = navController::popBackStack,
            )
        }
        composable(route = USER_EVENT_LIST_ROUTE) {
            UserMeetingEventListScreen(
                onEventClick = { navController.navigate("$EVENT_ROUTE_BASE/${it.uid}") },
                onBackCLick = navController::popBackStack,
            )
        }
    }
}

fun NavHostController.navigateToEvent(event: MeetingEventModel) {
    navigate("$EVENT_ROUTE_BASE/${event.uid}")
}