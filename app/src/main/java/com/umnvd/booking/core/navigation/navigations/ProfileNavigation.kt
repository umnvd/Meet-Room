package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.presentation.main.viewmodel.MainViewModel
import com.umnvd.booking.presentation.profile.ProfileScreen

const val PROFILE_ROUTE = "profile"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.profile(
    navHostController: NavHostController,
    onSignedOut: () -> Unit,
) {
    composable(PROFILE_ROUTE) {
        val parentEntry = remember(it) {
            navHostController.getBackStackEntry(EVENTS_HOME_ROUTE)
        }
        val mainViewModel = hiltViewModel<MainViewModel>(parentEntry)

        ProfileScreen(
            mainViewModel = mainViewModel,
            onSignedOut = onSignedOut,
        )
    }
}