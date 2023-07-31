package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.presentation.events.home.calendar.MeetingEventCalendarScreen
import com.umnvd.booking.presentation.events.home.schedule.MeetingEventScheduleScreen
import java.time.LocalDate

const val EVENT_SCHEDULE_GRAPH_DATE_KEY = "date"
const val EVENT_SCHEDULE_GRAPH_ROUTE = "event_schedule_graph"

private const val EVENT_SCHEDULE_ROUTE_BASE = "event_schedule"
private const val EVENT_SCHEDULE_ROUTE =
    "$EVENT_SCHEDULE_ROUTE_BASE?$EVENT_SCHEDULE_GRAPH_DATE_KEY={$EVENT_SCHEDULE_GRAPH_DATE_KEY}"

private const val EVENT_CALENDAR_ROUTE_BASE = "event_calendar"
const val EVENT_CALENDAR_ROUTE =
    "$EVENT_CALENDAR_ROUTE_BASE?$EVENT_SCHEDULE_GRAPH_DATE_KEY={$EVENT_SCHEDULE_GRAPH_DATE_KEY}"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.meetingEventScheduleGraph(
    navController: NavController,
    navigateToEvent: (MeetingEventModel) -> Unit,
) {
    navigation(
        startDestination = EVENT_SCHEDULE_ROUTE,
        route = EVENT_SCHEDULE_GRAPH_ROUTE,
    ) {
        composable(
            route = EVENT_SCHEDULE_ROUTE,
            arguments = listOf(
                navArgument(EVENT_SCHEDULE_GRAPH_DATE_KEY) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = LocalDate.now().toString()
                }),
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Up) },
            exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Down) },
            popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Up) },
            popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Down) }
        ) {
            MeetingEventScheduleScreen(onEventCLick = navigateToEvent)
        }
        composable(
            route = EVENT_CALENDAR_ROUTE,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Down) },
            exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Up) },
            popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Down) },
            popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Up) }
        ) {
            val currentDate = it.arguments
                ?.getString(EVENT_SCHEDULE_GRAPH_DATE_KEY)
                ?.let(LocalDate::parse)
                ?: LocalDate.now()

            MeetingEventCalendarScreen(
                currentDate = currentDate,
                onDayClick = navController::navigateToEventSchedule,
            )
        }
    }
}

fun NavController.navigateToEventCalendar(currentDate: LocalDate) {
    navigate("$EVENT_CALENDAR_ROUTE_BASE?$EVENT_SCHEDULE_GRAPH_DATE_KEY=$currentDate")
}

private fun NavController.navigateToEventSchedule(date: LocalDate) {
    navigate("$EVENT_SCHEDULE_ROUTE_BASE?$EVENT_SCHEDULE_GRAPH_DATE_KEY=$date") {
        popBackStack(route = EVENT_SCHEDULE_ROUTE, inclusive = true)
    }
}