package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.presentation.home.HomeScreen

const val HOME_ROUTE = "home"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.home() {
    composable(HOME_ROUTE) {
        HomeScreen()
    }
}

fun NavHostController.navigateToHome() {
    navigate(HOME_ROUTE)
}