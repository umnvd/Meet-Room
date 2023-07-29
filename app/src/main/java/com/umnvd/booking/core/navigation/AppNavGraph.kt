@file:OptIn(ExperimentalAnimationApi::class)

package com.umnvd.booking.core.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.umnvd.booking.core.navigation.navigations.SIGN_IN_ROUTE
import com.umnvd.booking.core.navigation.navigations.home
import com.umnvd.booking.core.navigation.navigations.navigateToHome
import com.umnvd.booking.core.navigation.navigations.navigateToSignIn
import com.umnvd.booking.core.navigation.navigations.signIn

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = SIGN_IN_ROUTE,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = NavigationTransitions.enter,
        exitTransition = NavigationTransitions.exit,
        popEnterTransition = NavigationTransitions.popEnter,
        popExitTransition = NavigationTransitions.popExit,
        modifier = modifier,
    ) {
        signIn(onSignedIn = navController::navigateToHome)
        home(onSignedOut = navController::navigateToSignIn)
    }
}