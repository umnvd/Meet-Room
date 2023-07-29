package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.presentation.profile.ProfileScreen

const val PROFILE_ROUTE = "profile"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.profile(onSignedOut: () -> Unit) {
    composable(PROFILE_ROUTE) {
        ProfileScreen(onSignedOut = onSignedOut)
    }
}