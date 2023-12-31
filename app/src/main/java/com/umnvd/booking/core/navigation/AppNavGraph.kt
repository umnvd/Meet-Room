package com.umnvd.booking.core.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.umnvd.booking.core.navigation.navigations.SIGN_IN_ROUTE
import com.umnvd.booking.core.navigation.navigations.main
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
        modifier = modifier,
        enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right) },
        popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
        popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right) },
    ) {
        signIn(onSignedIn = navController::navigateToHome)
        main(onSignedOut = navController::navigateToSignIn)
    }
}