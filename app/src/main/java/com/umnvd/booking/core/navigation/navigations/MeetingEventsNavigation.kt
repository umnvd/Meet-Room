package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.presentation.events.creation.MeetingEventCreationScreen
import com.umnvd.booking.presentation.events.event.MeetingEventScreen
import com.umnvd.booking.presentation.events.home.MeetingEventsHomeScreen
import com.umnvd.booking.presentation.events.user.UserMeetingEventListScreen
import com.umnvd.booking.presentation.main.viewmodel.MainViewModel

const val EVENTS_GRAPH_ROUTE = "events_graph"
const val EVENT_ROUTE_UID_KEY = "uid"
const val EVENTS_HOME_ROUTE = "events"
const val CREATE_EVENT_ROUTE_DATE_KEY = "date"

private const val EVENT_ROUTE_BASE = "event"
private const val EVENT_ROUTE = "$EVENT_ROUTE_BASE/{$EVENT_ROUTE_UID_KEY}"
private const val CREATE_EVENT_ROUTE_BASE = "create_event"
private const val CREATE_EVENT_ROUTE =
    "$CREATE_EVENT_ROUTE_BASE?$CREATE_EVENT_ROUTE_DATE_KEY={$CREATE_EVENT_ROUTE_DATE_KEY}"
private const val USER_EVENT_LIST_ROUTE = "user_event_list"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.meetingEventsGraph(
    navHostController: NavHostController,
) {
    navigation(startDestination = EVENTS_HOME_ROUTE, route = EVENTS_GRAPH_ROUTE) {
        composable(route = EVENTS_HOME_ROUTE) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(EVENTS_HOME_ROUTE)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parentEntry)

            MeetingEventsHomeScreen(
                mainViewModel = mainViewModel,
                onCreateClick = { date ->
                    navHostController.navigate(
                        "$CREATE_EVENT_ROUTE_BASE?$CREATE_EVENT_ROUTE_DATE_KEY=$date"
                    )
                },
                onMyEventsClick = { navHostController.navigate(USER_EVENT_LIST_ROUTE) },
                navigateToEvent = navHostController::navigateToEvent,
                homeNavController = navHostController,
            )
        }
        composable(
            route = EVENT_ROUTE,
            arguments = listOf(navArgument(EVENT_ROUTE_UID_KEY) { type = NavType.StringType })
        ) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(EVENTS_HOME_ROUTE)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parentEntry)

            MeetingEventScreen(
                mainViewModel = mainViewModel,
                onSaved = navHostController::popBackStack,
                onDeleted = navHostController::popBackStack,
                onBackClick = navHostController::popBackStack,
            )
        }
        composable(
            route = CREATE_EVENT_ROUTE,
            arguments = listOf(
                navArgument(CREATE_EVENT_ROUTE_DATE_KEY) {
                    type = NavType.StringType
                    nullable = true
                }
            ),
        ) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(EVENTS_HOME_ROUTE)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parentEntry)

            MeetingEventCreationScreen(
                mainViewModel = mainViewModel,
                onCreated = navHostController::popBackStack,
                onBackClick = navHostController::popBackStack,
            )
        }
        composable(route = USER_EVENT_LIST_ROUTE) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(EVENTS_HOME_ROUTE)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parentEntry)

            UserMeetingEventListScreen(
                mainViewModel = mainViewModel,
                onEventClick = { navHostController.navigate("$EVENT_ROUTE_BASE/${it.uid}") },
                onBackCLick = navHostController::popBackStack,
            )
        }
    }
}

fun NavHostController.navigateToEvent(event: MeetingEventModel) {
    navigate("$EVENT_ROUTE_BASE/${event.uid}")
}