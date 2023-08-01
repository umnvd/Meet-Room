package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.presentation.sign_in.SignInScreen

const val SIGN_IN_ROUTE = "sign_in"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.signIn(onSignedIn: () -> Unit) {
    composable(SIGN_IN_ROUTE) {
        SignInScreen(onSignedIn = onSignedIn)
    }
}

fun NavHostController.navigateToSignIn() {
    popBackStack(MAIN_ROUTE, true)
    navigate(SIGN_IN_ROUTE)
}