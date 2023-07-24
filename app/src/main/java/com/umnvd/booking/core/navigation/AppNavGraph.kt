@file:OptIn(ExperimentalAnimationApi::class)

package com.umnvd.booking.core.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.umnvd.booking.core.navigation.navigations.authScreen
import com.umnvd.booking.core.navigation.navigations.authScreenRoute
import com.umnvd.booking.core.navigation.navigations.meetingEventsScreen
import com.umnvd.booking.core.navigation.navigations.navigateToMeetingEvents

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = authScreenRoute,
        enterTransition = NavigationTransitions.enter,
        exitTransition = NavigationTransitions.exit,
        popEnterTransition = NavigationTransitions.popEnter,
        popExitTransition = NavigationTransitions.popExit,
        modifier = modifier,
        ) {
        authScreen(onSignedIn = navController::navigateToMeetingEvents)
        meetingEventsScreen(onBack = navController::popBackStack)
    }
}