package com.umnvd.booking.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.umnvd.booking.core.navigation.AppNavGraph
import com.umnvd.booking.core.navigation.navigations.authScreenRoute
import com.umnvd.booking.core.navigation.navigations.meetingEventsScreenRoute
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { viewModel.authState.value == AuthState.UNKNOWN }

        setContent {
            val navController = rememberAnimatedNavController()
            val authState = viewModel.authState.collectAsStateWithLifecycle()

            MeetingRoomBookingTheme {
                AppNavGraph(
                    navController = navController,
                    startDestination =
                    if (authState.value == AuthState.AUTHORIZED) meetingEventsScreenRoute
                    else authScreenRoute,
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                currentFocus?.clearFocus()
                            })
                        },
                )
            }
        }
    }
}