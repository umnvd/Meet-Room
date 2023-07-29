package com.umnvd.booking.core.navigation.navigations

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.presentation.events.MeetingEventsHomeScreen
import com.umnvd.booking.presentation.events.creation.MeetingEventCreationScreen
import com.umnvd.booking.presentation.events.event.MeetingEventScreen
import com.umnvd.booking.presentation.events.user.UserMeetingEventListScreen

const val EVENTS_GRAPH_ROUTE = "events_graph"
const val EVENT_ROUTE_UID_KEY = "uid"

private const val EVENTS_HOME_ROUTE = "events"
private const val EVENT_ROUTE_BASE = "event/"
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
            )
        }
        composable(
            route = EVENT_ROUTE,
            arguments = listOf(navArgument(EVENT_ROUTE_UID_KEY) { type = NavType.StringType })
        ) {
            val uid = it.arguments?.getString(EVENT_ROUTE_UID_KEY)
                ?: throw IllegalStateException("Meeting event UID not specified.")
            Log.d("Navigation", it.destination.toString())
            MeetingEventScreen(
                onEdited = navController::popBackStack,
                onBackCLick = navController::popBackStack,
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

fun NavController.navigateToEvent(uid: String) {
    navigate("$EVENT_ROUTE_BASE$uid")
}