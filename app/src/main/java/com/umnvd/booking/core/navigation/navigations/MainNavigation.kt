package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.presentation.main.MainScreen

const val MAIN_ROUTE = "main"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.main(onSignedOut: () -> Unit) {
    composable(MAIN_ROUTE) {
        MainScreen(onSignedOut = onSignedOut)
    }
}

fun NavHostController.navigateToHome() {
    popBackStack(SIGN_IN_ROUTE, true)
    navigate(MAIN_ROUTE)
}