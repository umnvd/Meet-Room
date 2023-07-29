package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.presentation.events.schedule.calendar.MeetingEventCalendarScreen
import com.umnvd.booking.presentation.events.schedule.schedule.MeetingEventScheduleScreen

const val EVENT_SCHEDULE_GRAPH_ROUTE = "event_schedule_graph"

private const val EVENT_SCHEDULE_ROUTE = "event_schedule"
private const val EVENT_CALENDAR_ROUTE = "event_calendar"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.meetingEventScheduleGraph(
    navController: NavController,
) {
    navigation(startDestination = EVENT_SCHEDULE_ROUTE, route = EVENT_SCHEDULE_GRAPH_ROUTE) {
        composable(route = EVENT_SCHEDULE_ROUTE) {
            MeetingEventScheduleScreen(
                onEventCLick = navController::navigateToEvent,
                onMonthClick = { navController.navigate(EVENT_CALENDAR_ROUTE) },
            )
        }
        composable(route = EVENT_CALENDAR_ROUTE) {
            MeetingEventCalendarScreen(onBackCLick = navController::popBackStack)
        }
    }
}