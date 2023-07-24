package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.presentation.auth.AuthScreen

const val authScreenRoute = "auth"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authScreen(onSignedIn: () -> Unit) {
    composable(authScreenRoute) {
        AuthScreen(onSignedIn = onSignedIn)
    }
}

fun NavHostController.navigateToAuth() {
    navigate(authScreenRoute)
}