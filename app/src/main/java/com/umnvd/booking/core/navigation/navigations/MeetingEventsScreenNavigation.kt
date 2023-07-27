@file:OptIn(ExperimentalAnimationApi::class)

package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.presentation.events.MeetingEventsScreen

const val meetingEventsScreenRoute = "meeting_events"

fun NavGraphBuilder.meetingEventsScreen(onBack: () -> Unit) {
    composable(meetingEventsScreenRoute) {
        MeetingEventsScreen(onBack = onBack)
    }
}

fun NavHostController.navigateToMeetingEvents() {
    navigate(meetingEventsScreenRoute)
}