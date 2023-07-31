package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.presentation.events.home.calendar.MeetingEventCalendarScreen
import com.umnvd.booking.presentation.events.home.schedule.MeetingEventScheduleScreen
import com.umnvd.booking.presentation.events.home.viewmodel.MeetingEventsHomeScreenViewModel

const val EVENT_SCHEDULE_GRAPH_ROUTE = "event_schedule_graph"

private const val EVENT_SCHEDULE_ROUTE = "event_schedule"
const val EVENT_CALENDAR_ROUTE = "event_calendar"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.meetingEventScheduleGraph(
    navController: NavHostController,
    homeNavController: NavHostController,
    navigateToEvent: (MeetingEventModel) -> Unit,
) {
    navigation(
        startDestination = EVENT_SCHEDULE_ROUTE,
        route = EVENT_SCHEDULE_GRAPH_ROUTE,
    ) {
        composable(
            route = EVENT_SCHEDULE_ROUTE,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Up) },
            exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Down) },
            popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Up) },
            popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Down) },
        ) {
            val parentEntry = remember(it) {
                homeNavController.getBackStackEntry(EVENTS_HOME_ROUTE)
            }
            val homeViewModel = hiltViewModel<MeetingEventsHomeScreenViewModel>(parentEntry)

            MeetingEventScheduleScreen(
                viewModel = homeViewModel,
                onEventCLick = navigateToEvent,
            )
        }
        composable(
            route = EVENT_CALENDAR_ROUTE,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Down) },
            exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Up) },
            popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Down) },
            popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Up) },
        ) {
            val parentEntry = remember(it) {
                homeNavController.getBackStackEntry(EVENTS_HOME_ROUTE)
            }
            val homeViewModel = hiltViewModel<MeetingEventsHomeScreenViewModel>(parentEntry)

            MeetingEventCalendarScreen(
                viewModel = homeViewModel,
                onDayClick = navController::navigateToEventSchedule,
            )
        }
    }
}

fun NavHostController.navigateToEventCalendar() {
    navigate(EVENT_CALENDAR_ROUTE)
}

fun NavHostController.navigateToEventSchedule() {
    navigate(EVENT_SCHEDULE_ROUTE) {
        popBackStack(route = EVENT_SCHEDULE_ROUTE, inclusive = true)
    }
}