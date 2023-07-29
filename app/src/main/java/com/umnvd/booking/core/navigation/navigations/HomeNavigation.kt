package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.presentation.home.HomeScreen

const val HOME_ROUTE = "home"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.home(onSignedOut: () -> Unit) {
    composable(HOME_ROUTE) {
        HomeScreen(onSignedOut)
    }
}

fun NavHostController.navigateToHome() {
    popBackStack(SIGN_IN_ROUTE, true)
    navigate(HOME_ROUTE)
}