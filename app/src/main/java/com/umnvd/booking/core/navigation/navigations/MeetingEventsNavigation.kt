package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.presentation.events.creation.MeetingEventCreationScreen
import com.umnvd.booking.presentation.events.event.MeetingEventScreen
import com.umnvd.booking.presentation.events.home.MeetingEventsHomeScreen
import com.umnvd.booking.presentation.events.user.UserMeetingEventListScreen

const val EVENTS_GRAPH_ROUTE = "events_graph"
const val EVENT_ROUTE_UID_KEY = "uid"

private const val EVENTS_HOME_ROUTE = "events"
private const val EVENT_ROUTE_BASE = "event"
private const val EVENT_ROUTE = "$EVENT_ROUTE_BASE/{$EVENT_ROUTE_UID_KEY}"
private const val CREATE_EVENT_ROUTE = "create_event"
private const val USER_EVENT_LIST_ROUTE = "user_event_list"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.meetingEventsGraph(
    navController: NavController,
) {
    navigation(startDestination = EVENTS_HOME_ROUTE, route = EVENTS_GRAPH_ROUTE) {
        composable(route = EVENTS_HOME_ROUTE) {
            MeetingEventsHomeScreen(
                onCreateClick = { navController.navigate(CREATE_EVENT_ROUTE) },
                navigateToEvent = navController::navigateToEvent,
            )
        }
        composable(
            route = EVENT_ROUTE,
            arguments = listOf(navArgument(EVENT_ROUTE_UID_KEY) { type = NavType.StringType })
        ) {
            MeetingEventScreen(
                onSaved = navController::popBackStack,
                onDeleted = navController::popBackStack,
                onBackClick = navController::popBackStack,
            )
        }
        composable(route = CREATE_EVENT_ROUTE) {
            MeetingEventCreationScreen(
                onCreated = navController::popBackStack,
                onBackClick = navController::popBackStack,
            )
        }
        composable(route = USER_EVENT_LIST_ROUTE) {
            UserMeetingEventListScreen(
                onEventClick = {
                    navController.navigate(EVENT_ROUTE.replace(EVENT_ROUTE_UID_KEY, it))
                },
                onBackCLick = navController::popBackStack,
            )
        }
    }
}

fun NavController.navigateToEvent(event: MeetingEventModel) {
    navigate("$EVENT_ROUTE_BASE/${event.uid}")
}