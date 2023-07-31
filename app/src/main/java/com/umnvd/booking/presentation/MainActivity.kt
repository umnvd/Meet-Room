package com.umnvd.booking.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.umnvd.booking.core.navigation.AppNavGraph
import com.umnvd.booking.core.navigation.navigations.HOME_ROUTE
import com.umnvd.booking.core.navigation.navigations.SIGN_IN_ROUTE
import com.umnvd.booking.core.ui.components.AppErrorSnackbarProvider
import com.umnvd.booking.core.ui.components.AppProgressIndicatorProvider
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.domain.auth.models.AuthState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class, ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        splashScreen.setKeepOnScreenCondition { viewModel.state.value == AuthState.UNKNOWN }

        setContent {
            val navController = rememberAnimatedNavController()
            val authState = viewModel.state.collectAsStateWithLifecycle()

            MeetingRoomBookingTheme {
                AppProgressIndicatorProvider {
                    AppErrorSnackbarProvider { innerPadding ->
                        AppNavGraph(
                            navController = navController,
                            startDestination = when (authState.value) {
                                AuthState.AUTHORIZED -> HOME_ROUTE
                                else -> SIGN_IN_ROUTE
                            },
                            modifier = Modifier
                                .padding(innerPadding)
                                .consumeWindowInsets(innerPadding)
                                .imePadding()
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
    }
}