package com.umnvd.booking.core.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalAnimationApi::class)
object NavigationTransitions {
    val enter: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
        slideIntoContainer(
            AnimatedContentScope.SlideDirection.Left,
        )
    }

    val exit: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutOfContainer(
            AnimatedContentScope.SlideDirection.Left,
        )
    }

    val popEnter: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition =
        {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
            )
        }

    val popExit: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition =
        {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
            )
        }
}